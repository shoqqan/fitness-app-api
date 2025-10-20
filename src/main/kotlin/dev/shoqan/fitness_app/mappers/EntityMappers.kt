package dev.shoqan.fitness_app.mappers

import dev.shoqan.fitness_app.dto.*
import dev.shoqan.fitness_app.entities.*

// User Mappers
fun UserEntity.toResponse(): UserResponse = UserResponse(
    id = id,
    username = username,
    createdAt = createdAt,
    updatedAt = updatedAt
)

// Workout Mappers
fun WorkoutEntity.toResponse(includeExercises: Boolean = false): WorkoutResponse = WorkoutResponse(
    id = id,
    title = title,
    date = date,
    userId = user.id,
    exercises = if (includeExercises) exercises.map { it.toResponse() } else emptyList(),
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun WorkoutEntity.toSummaryResponse(): WorkoutSummaryResponse = WorkoutSummaryResponse(
    id = id,
    title = title,
    date = date,
    userId = user.id,
    exerciseCount = exercises.size,
    totalVolume = exercises.sumOf { it.getTotalVolume() },
    totalReps = exercises.sumOf { it.getTotalReps() },
    createdAt = createdAt
)

// WorkoutExercise Mappers
fun WorkoutExerciseEntity.toResponse(includeSets: Boolean = true): WorkoutExerciseResponse = WorkoutExerciseResponse(
    id = id,
    workoutId = workout.id,
    exerciseLibraryId = exerciseLibrary.id,
    exerciseName = exerciseLibrary.name,
    restSeconds = restSeconds,
    orderIndex = orderIndex,
    sets = if (includeSets) sets.map { it.toResponse() } else emptyList(),
    totalVolume = getTotalVolume(),
    totalReps = getTotalReps(),
    createdAt = createdAt,
    updatedAt = updatedAt
)

// Set Mappers
fun SetEntity.toResponse(): SetResponse = SetResponse(
    id = id,
    workoutExerciseId = workoutExercise.id,
    weight = weight,
    reps = reps,
    orderIndex = orderIndex,
    createdAt = createdAt,
    updatedAt = updatedAt
)

// ExerciseLibrary Mappers
fun ExerciseLibraryEntity.toResponse(): ExerciseLibraryResponse = ExerciseLibraryResponse(
    id = id,
    name = name,
    description = description,
    muscleGroup = muscleGroup,
    category = category,
    isCustom = isCustom,
    isPublic = isPublic,
    createdById = createdBy?.id,
    createdAt = createdAt,
    updatedAt = updatedAt
)