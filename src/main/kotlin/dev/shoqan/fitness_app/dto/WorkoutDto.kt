package dev.shoqan.fitness_app.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
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

data class WorkoutSummaryResponse(
    val id: UUID,
    val title: String,
    val date: OffsetDateTime,
    val userId: UUID,
    val exerciseCount: Int,
    val totalVolume: Double,
    val totalReps: Int,
    val createdAt: OffsetDateTime
)

data class CreateWorkoutRequest(
    @field:NotBlank(message = "Title is required")
    val title: String,

    @field:NotNull(message = "Date is required")
    val date: OffsetDateTime,

    @field:NotNull(message = "User ID is required")
    val userId: UUID
)

data class UpdateWorkoutRequest(
    val title: String?,
    val date: OffsetDateTime?
)