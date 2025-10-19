package dev.shoqan.fitness_app.domain.repository

import dev.shoqan.fitness_app.infrastructure.persistence.entity.WorkoutEntity
import java.time.OffsetDateTime
import java.util.UUID

interface WorkoutRepository {
    fun findById(id: UUID): WorkoutEntity?
    fun findAll(): List<WorkoutEntity>
    fun findByUserId(userId: UUID): List<WorkoutEntity>
    fun findByUserIdOrderByDateDesc(userId: UUID): List<WorkoutEntity>
    fun findByUserIdAndDateBetween(userId: UUID, startDate: OffsetDateTime, endDate: OffsetDateTime): List<WorkoutEntity>
    fun save(workout: WorkoutEntity): WorkoutEntity
    fun deleteById(id: UUID)
    fun existsById(id: UUID): Boolean
}
