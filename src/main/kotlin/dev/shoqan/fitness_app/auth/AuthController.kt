package dev.shoqan.fitness_app.auth


import dev.shoqan.fitness_app.user.UserEntity
import dev.shoqan.fitness_app.user.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API для аутентификации и регистрации пользователей")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/login")
    @Operation(
        summary = "Вход в систему",
        description = "Аутентифицирует пользователя и возвращает JWT токен"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Успешная аутентификация",
                content = [Content(schema = Schema(implementation = AuthResponse::class))]
            ),
            ApiResponse(responseCode = "401", description = "Неверные учетные данные")
        ]
    )
    fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<AuthResponse> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtUtil.generateToken(authRequest.username)
        return ResponseEntity.ok(AuthResponse(token = token, message = "Login successful"))
    }

    @PostMapping("/register")
    @Operation(
        summary = "Регистрация нового пользователя",
        description = "Создает нового пользователя и возвращает JWT токен"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Пользователь успешно зарегистрирован",
                content = [Content(schema = Schema(implementation = AuthResponse::class))]
            ),
            ApiResponse(responseCode = "409", description = "Имя пользователя уже занято")
        ]
    )
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<AuthResponse> {
        if (userService.existsByUsername(registerRequest.username)) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(AuthResponse(message = "Username already exists"))
        }

        val user = UserEntity(
            username = registerRequest.username,
            password = passwordEncoder.encode(registerRequest.password)
        )

        userService.save(user)

        val token = jwtUtil.generateToken(user.username)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(AuthResponse(token = token, message = "Registration successful"))
    }
}

@Schema(description = "Запрос на вход в систему")
data class AuthRequest(
    @Schema(description = "Имя пользователя", example = "john_doe")
    val username: String,
    @Schema(description = "Пароль пользователя", example = "password123")
    val password: String,
)

@Schema(description = "Запрос на регистрацию нового пользователя")
data class RegisterRequest(
    @Schema(description = "Имя пользователя (уникальное)", example = "john_doe")
    val username: String,
    @Schema(description = "Пароль пользователя (минимум 6 символов)", example = "password123")
    val password: String,
)

@Schema(description = "Ответ на запрос аутентификации/регистрации")
data class AuthResponse(
    @Schema(description = "JWT токен для доступа к защищенным эндпоинтам", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val token: String? = null,
    @Schema(description = "Сообщение о результате операции", example = "Login successful")
    val message: String
)