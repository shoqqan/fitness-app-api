package dev.shoqan.fitness_app.user

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.OffsetDateTime
import java.util.UUID

data class UserResponse(
    val id: UUID,
    val username: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class CreateUserRequest(
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    val username: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    val password: String
)

data class UpdateUserRequest(
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    val username: String?,

    @field:Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    val password: String?
)