package dev.shoqan.fitness_app.infrastructure.persistence.repositories

import dev.shoqan.fitness_app.domain.repository.WorkoutRepository
import dev.shoqan.fitness_app.infrastructure.persistence.entity.WorkoutEntity
import dev.shoqan.fitness_app.infrastructure.persistence.jpa.WorkoutJpaRepository
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.UUID

@Component
class WorkoutRepositoryImpl(
    private val jpaRepository: WorkoutJpaRepository
) : WorkoutRepository {

    override fun findById(id: UUID): WorkoutEntity? {
        return jpaRepository.findById(id).orElse(null)
    }

    override fun findAll(): List<WorkoutEntity> {
        return jpaRepository.findAll()
    }

    override fun findByUserId(userId: UUID): List<WorkoutEntity> {
        return jpaRepository.findByUserId(userId)
    }

    override fun findByUserIdOrderByDateDesc(userId: UUID): List<WorkoutEntity> {
        return jpaRepository.findByUserIdOrderByDateDesc(userId)
    }

    override fun findByUserIdAndDateBetween(
        userId: UUID,
        startDate: OffsetDateTime,
        endDate: OffsetDateTime
    ): List<WorkoutEntity> {
        return jpaRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
    }

    override fun save(workout: WorkoutEntity): WorkoutEntity {
        return jpaRepository.save(workout)
    }

    override fun deleteById(id: UUID) {
        jpaRepository.deleteById(id)
    }

    override fun existsById(id: UUID): Boolean {
        return jpaRepository.existsById(id)
    }
}