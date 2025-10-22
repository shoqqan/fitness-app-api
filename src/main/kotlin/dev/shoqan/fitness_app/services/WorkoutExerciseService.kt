package dev.shoqan.fitness_app.services

import dev.shoqan.fitness_app.entities.WorkoutExerciseEntity
import dev.shoqan.fitness_app.repositories.WorkoutExerciseRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WorkoutExerciseService(
    private val jpaRepository: WorkoutExerciseRepository
)  {

    fun findById(id: UUID): WorkoutExerciseEntity? {
        return jpaRepository.findById(id).orElse(null)
    }

     fun findAll(): List<WorkoutExerciseEntity> {
        return jpaRepository.findAll()
    }

     fun findByWorkoutId(workoutId: UUID): List<WorkoutExerciseEntity> {
        return jpaRepository.findByWorkoutId(workoutId)
    }

    fun save(workoutExercise: WorkoutExerciseEntity): WorkoutExerciseEntity {
        return jpaRepository.save(workoutExercise)
    }

     fun deleteById(id: UUID) {
        jpaRepository.deleteById(id)
    }

    fun existsById(id: UUID): Boolean {
        return jpaRepository.existsById(id)
    }
}