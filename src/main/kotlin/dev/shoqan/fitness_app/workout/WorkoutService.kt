package dev.shoqan.fitness_app.workout

import dev.shoqan.fitness_app.workout.WorkoutEntity
import dev.shoqan.fitness_app.workout.WorkoutRepository
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.UUID

@Component
class WorkoutService(
    private val jpaRepository: WorkoutRepository
)  {

     fun findById(id: UUID): WorkoutEntity? {
        return jpaRepository.findById(id).orElse(null)
    }

    fun findAll(): List<WorkoutEntity> {
        return jpaRepository.findAll()
    }

    fun findByUserId(userId: UUID): List<WorkoutEntity> {
        return jpaRepository.findByUserId(userId)
    }

     fun findByUserIdOrderByDateDesc(userId: UUID): List<WorkoutEntity> {
        return jpaRepository.findByUserIdOrderByDateDesc(userId)
    }

    fun findByUserIdAndDateBetween(
        userId: UUID,
        startDate: OffsetDateTime,
        endDate: OffsetDateTime
    ): List<WorkoutEntity> {
        return jpaRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
    }

     fun save(workout: WorkoutEntity): WorkoutEntity {
        return jpaRepository.save(workout)
    }

    fun deleteById(id: UUID) {
        jpaRepository.deleteById(id)
    }

     fun existsById(id: UUID): Boolean {
        return jpaRepository.existsById(id)
    }
}