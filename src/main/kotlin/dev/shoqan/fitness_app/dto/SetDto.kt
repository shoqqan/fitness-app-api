package dev.shoqan.fitness_app.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime
import java.util.UUID

data class SetResponse(
    val id: UUID,
    val workoutExerciseId: UUID,
    val weight: Double,
    val reps: Int,
    val orderIndex: Int,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class CreateSetRequest(
    @field:NotNull(message = "Workout Exercise ID is required")
    var workoutExerciseId: UUID,

    @field:Min(value = 0, message = "Weight cannot be negative")
    val weight: Double = 0.0,

    @field:Min(value = 0, message = "Reps cannot be negative")
    val reps: Int = 0,

    @field:Min(value = 0, message = "Order index cannot be negative")
    val orderIndex: Int
)

data class UpdateSetRequest(
    @field:Min(value = 0, message = "Weight cannot be negative")
    val weight: Double?,

    @field:Min(value = 0, message = "Reps cannot be negative")
    val reps: Int?
)