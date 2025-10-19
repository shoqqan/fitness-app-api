package dev.shoqan.fitness_app.infrastructure.persistence.repositories

import dev.shoqan.fitness_app.domain.repository.WorkoutExerciseRepository
import dev.shoqan.fitness_app.infrastructure.persistence.entity.WorkoutExerciseEntity
import dev.shoqan.fitness_app.infrastructure.persistence.jpa.WorkoutExerciseJpaRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WorkoutExerciseRepositoryImpl(
    private val jpaRepository: WorkoutExerciseJpaRepository
) : WorkoutExerciseRepository {

    override fun findById(id: UUID): WorkoutExerciseEntity? {
        return jpaRepository.findById(id).orElse(null)
    }

    override fun findAll(): List<WorkoutExerciseEntity> {
        return jpaRepository.findAll()
    }

    override fun findByWorkoutId(workoutId: UUID): List<WorkoutExerciseEntity> {
        return jpaRepository.findByWorkoutId(workoutId)
    }

    override fun findByWorkoutIdOrderByOrderIndexAsc(workoutId: UUID): List<WorkoutExerciseEntity> {
        return jpaRepository.findByWorkoutIdOrderByOrderIndexAsc(workoutId)
    }

    override fun findByExerciseLibraryId(exerciseLibraryId: UUID): List<WorkoutExerciseEntity> {
        return jpaRepository.findByExerciseLibraryId(exerciseLibraryId)
    }

    override fun save(workoutExercise: WorkoutExerciseEntity): WorkoutExerciseEntity {
        return jpaRepository.save(workoutExercise)
    }

    override fun deleteById(id: UUID) {
        jpaRepository.deleteById(id)
    }

    override fun existsById(id: UUID): Boolean {
        return jpaRepository.existsById(id)
    }
}