package dev.shoqan.fitness_app.services

import dev.shoqan.fitness_app.entities.SetEntity
import dev.shoqan.fitness_app.repositories.SetJpaRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SetService(
    private val jpaRepository: SetJpaRepository
) {

     fun findById(id: UUID): SetEntity? {
        return jpaRepository.findById(id).orElse(null)
    }

     fun findAll(): List<SetEntity> {
        return jpaRepository.findAll()
    }

    fun findByWorkoutExerciseId(workoutExerciseId: UUID): List<SetEntity> {
        return jpaRepository.findByWorkoutExerciseId(workoutExerciseId)
    }

    fun findByWorkoutExerciseIdOrderByOrderIndexAsc(workoutExerciseId: UUID): List<SetEntity> {
        return jpaRepository.findByWorkoutExerciseIdOrderByOrderIndexAsc(workoutExerciseId)
    }

     fun save(set: SetEntity): SetEntity {
        return jpaRepository.save(set)
    }

     fun deleteById(id: UUID) {
        jpaRepository.deleteById(id)
    }

     fun existsById(id: UUID): Boolean {
        return jpaRepository.existsById(id)
    }
}