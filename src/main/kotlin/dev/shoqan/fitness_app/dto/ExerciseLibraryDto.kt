package dev.shoqan.fitness_app.dto

import jakarta.validation.constraints.NotBlank
import java.time.OffsetDateTime
import java.util.UUID

data class ExerciseLibraryResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val muscleGroup: String?,
    val category: String?,
    val isCustom: Boolean,
    val isPublic: Boolean,
    val createdById: UUID?,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class CreateExerciseLibraryRequest(
    @field:NotBlank(message = "Exercise name is required")
    val name: String,

    val description: String? = null,
    val muscleGroup: String? = null,
    val category: String? = null,
    val isPublic: Boolean = false,
    val createdById: UUID? = null
)

data class UpdateExerciseLibraryRequest(
    val name: String?,
    val description: String?,
    val muscleGroup: String?,
    val category: String?,
    val isPublic: Boolean?
)

data class ExerciseSearchRequest(
    val query: String = "",
    val muscleGroup: String? = null,
    val userId: UUID? = null
)