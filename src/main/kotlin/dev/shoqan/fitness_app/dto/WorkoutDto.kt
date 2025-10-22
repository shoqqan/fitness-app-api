package dev.shoqan.fitness_app.dto

import jakarta.validation.constraints.NotBlank
import java.time.OffsetDateTime
import java.util.UUID

data class WorkoutResponse(
    val id: UUID,
    val title: String,
    val date: OffsetDateTime,
    val userId: UUID,
    val exercises: List<WorkoutExerciseResponse> = emptyList(),
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class CreateWorkoutRequest(
    @field:NotBlank(message = "Title is required")
    val title: String
)

data class UpdateWorkoutRequest(
    val title: String?,
    val date: OffsetDateTime?
)