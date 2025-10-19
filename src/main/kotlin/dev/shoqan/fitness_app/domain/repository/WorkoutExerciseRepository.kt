package dev.shoqan.fitness_app.domain.repository

import dev.shoqan.fitness_app.infrastructure.persistence.entity.WorkoutExerciseEntity
import java.util.UUID

interface WorkoutExerciseRepository {
    fun findById(id: UUID): WorkoutExerciseEntity?
    fun findAll(): List<WorkoutExerciseEntity>
    fun findByWorkoutId(workoutId: UUID): List<WorkoutExerciseEntity>
    fun findByWorkoutIdOrderByOrderIndexAsc(workoutId: UUID): List<WorkoutExerciseEntity>
    fun findByExerciseLibraryId(exerciseLibraryId: UUID): List<WorkoutExerciseEntity>
    fun save(workoutExercise: WorkoutExerciseEntity): WorkoutExerciseEntity
    fun deleteById(id: UUID)
    fun existsById(id: UUID): Boolean
}