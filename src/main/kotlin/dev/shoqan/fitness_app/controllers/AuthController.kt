package dev.shoqan.fitness_app.controllers


import dev.shoqan.fitness_app.auth.JwtUtil
import dev.shoqan.fitness_app.entities.UserEntity
import dev.shoqan.fitness_app.services.UserService
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
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<AuthResponse> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtUtil.generateToken(authRequest.username)
        return ResponseEntity.ok(AuthResponse(token = token, message = "Login successful"))
    }

    @PostMapping("/register")
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

data class AuthRequest(
    val username: String,
    val password: String,
)

data class RegisterRequest(
    val username: String,
    val password: String,
)

data class AuthResponse(
    val token: String? = null,
    val message: String
)