package dev.shoqan.fitness_app.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime
import java.util.UUID

data class WorkoutExerciseResponse(
    val id: UUID,
    val workoutId: UUID,
    val exerciseLibraryId: UUID,
    val exerciseName: String,
    val restSeconds: Int,
    val orderIndex: Int,
    val sets: List<SetResponse> = emptyList(),
    val totalVolume: Double,
    val totalReps: Int,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class CreateWorkoutExerciseRequest(
    @field:NotNull(message = "Workout ID is required")
    val workoutId: UUID,

    @field:NotNull(message = "Exercise Library ID is required")
    val exerciseLibraryId: UUID,

    @field:Min(value = 0, message = "Rest seconds cannot be negative")
    val restSeconds: Int = 60,

    @field:Min(value = 0, message = "Order index cannot be negative")
    val orderIndex: Int
)

data class UpdateWorkoutExerciseRequest(
    @field:Min(value = 0, message = "Rest seconds cannot be negative")
    val restSeconds: Int?,

    @field:Min(value = 0, message = "Order index cannot be negative")
    val orderIndex: Int?
)