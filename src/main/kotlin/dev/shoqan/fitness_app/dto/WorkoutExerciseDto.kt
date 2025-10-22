package dev.shoqan.fitness_app.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime
import java.util.UUID

data class WorkoutExerciseResponse(
    val id: UUID,
    val workoutId: UUID,
    val name: String,
    val restSeconds: Int,
    val sets: List<SetResponse> = emptyList(),
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class CreateWorkoutExerciseRequest(
    @field:NotNull(message = "Workout ID is required")
    var workoutId: UUID,

    @field:NotNull(message = "Exercise name is required")
    var name: String,

    @field:Min(value = 0, message = "Rest seconds cannot be negative")
    val restSeconds: Int = 60,

)

data class UpdateWorkoutExerciseRequest(
    @field:Min(value = 0, message = "Rest seconds cannot be negative")
    val restSeconds: Int?,

    @field:Min(value = 0, message = "Order index cannot be negative")
    val orderIndex: Int?
)