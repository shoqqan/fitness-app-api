package dev.shoqan.fitness_app.user

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        val users = userService.findAll()
        return ResponseEntity.ok(users.map { it.toResponse() })
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<UserResponse> {
        val user = userService.findById(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(user.toResponse())
    }

    @GetMapping("/username/{username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<UserResponse> {
        val user = userService.findByUsername(username)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(user.toResponse())
    }

    @PostMapping
    fun createUser(@Valid @RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        // Check if username already exists
        if (userService.existsByUsername(request.username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }

        val user = UserEntity(
            username = request.username,
            password = request.password // TODO: Hash password before saving!
        )

        val savedUser = userService.save(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser.toResponse())
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: UUID,
        @Valid @RequestBody request: UpdateUserRequest
    ): ResponseEntity<UserResponse> {
        val existingUser = userService.findById(id)
            ?: return ResponseEntity.notFound().build()

        // Check if new username is taken by another user
        request.username?.let { newUsername ->
            val userWithUsername = userService.findByUsername(newUsername)
            if (userWithUsername != null && userWithUsername.id != id) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build()
            }
        }

        // Update fields
        request.username?.let { existingUser.username = it }
        request.password?.let { existingUser.password = it } // TODO: Hash password!

        val updatedUser = userService.save(existingUser)
        return ResponseEntity.ok(updatedUser.toResponse())
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Void> {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build()
        }

        userService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}/exists")
    fun checkUserExists(@PathVariable id: UUID): ResponseEntity<Map<String, Boolean>> {
        val exists = userService.existsById(id)
        return ResponseEntity.ok(mapOf("exists" to exists))
    }

    private fun UserEntity.toResponse(): UserResponse = UserResponse(
        id = this.id!!,
        username = this.username,
        createdAt = this.createdAt!!,
        updatedAt = this.updatedAt!!
    )
}