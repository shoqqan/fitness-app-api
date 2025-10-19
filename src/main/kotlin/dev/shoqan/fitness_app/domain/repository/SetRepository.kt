package dev.shoqan.fitness_app.domain.repository

import dev.shoqan.fitness_app.infrastructure.persistence.entity.SetEntity
import java.util.UUID

interface SetRepository {
    fun findById(id: UUID): SetEntity?
    fun findAll(): List<SetEntity>
    fun findByWorkoutExerciseId(workoutExerciseId: UUID): List<SetEntity>
    fun findByWorkoutExerciseIdOrderByOrderIndexAsc(workoutExerciseId: UUID): List<SetEntity>
    fun save(set: SetEntity): SetEntity
    fun deleteById(id: UUID)
    fun existsById(id: UUID): Boolean
}