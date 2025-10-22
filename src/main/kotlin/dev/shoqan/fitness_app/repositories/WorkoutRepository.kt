package dev.shoqan.fitness_app.repositories

import dev.shoqan.fitness_app.entities.WorkoutEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.UUID

@Repository
interface WorkoutRepository : JpaRepository<WorkoutEntity, UUID> {
    fun findByUserId(userId: UUID): List<WorkoutEntity>
    fun findByUserIdOrderByDateDesc(userId: UUID): List<WorkoutEntity>
    fun findByUserIdAndDateBetween(userId: UUID, startDate: OffsetDateTime, endDate: OffsetDateTime): List<WorkoutEntity>
}