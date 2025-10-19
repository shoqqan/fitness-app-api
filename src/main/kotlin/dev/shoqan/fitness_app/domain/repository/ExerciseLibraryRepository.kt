package dev.shoqan.fitness_app.domain.repository

import dev.shoqan.fitness_app.infrastructure.persistence.entity.ExerciseLibraryEntity
import java.util.UUID

interface ExerciseLibraryRepository {
    fun findById(id: UUID): ExerciseLibraryEntity?
    fun findAll(): List<ExerciseLibraryEntity>
    fun findByNameIgnoreCase(name: String): ExerciseLibraryEntity?
    fun findByNameContainingIgnoreCase(query: String): List<ExerciseLibraryEntity>
    fun findByMuscleGroup(muscleGroup: String): List<ExerciseLibraryEntity>
    fun findByIsCustomFalse(): List<ExerciseLibraryEntity>
    fun findByCreatedById(userId: UUID): List<ExerciseLibraryEntity>
    fun searchAvailableExercises(userId: UUID, query: String): List<ExerciseLibraryEntity>
    fun save(exerciseLibrary: ExerciseLibraryEntity): ExerciseLibraryEntity
    fun deleteById(id: UUID)
    fun existsById(id: UUID): Boolean
}