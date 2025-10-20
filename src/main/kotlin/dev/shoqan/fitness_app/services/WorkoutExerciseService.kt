package dev.shoqan.fitness_app.services

import dev.shoqan.fitness_app.entities.WorkoutExerciseEntity
import dev.shoqan.fitness_app.repositories.WorkoutExerciseJpaRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WorkoutExerciseService(
    private val jpaRepository: WorkoutExerciseJpaRepository
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

     fun findByWorkoutIdOrderByOrderIndexAsc(workoutId: UUID): List<WorkoutExerciseEntity> {
        return jpaRepository.findByWorkoutIdOrderByOrderIndexAsc(workoutId)
    }

    fun findByExerciseLibraryId(exerciseLibraryId: UUID): List<WorkoutExerciseEntity> {
        return jpaRepository.findByExerciseLibraryId(exerciseLibraryId)
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