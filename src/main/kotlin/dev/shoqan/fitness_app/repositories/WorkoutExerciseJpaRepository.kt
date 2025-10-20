package dev.shoqan.fitness_app.repositories

import dev.shoqan.fitness_app.entities.WorkoutExerciseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface WorkoutExerciseJpaRepository : JpaRepository<WorkoutExerciseEntity, UUID> {
    fun findByWorkoutId(workoutId: UUID): List<WorkoutExerciseEntity>
    fun findByWorkoutIdOrderByOrderIndexAsc(workoutId: UUID): List<WorkoutExerciseEntity>
    fun findByExerciseLibraryId(exerciseLibraryId: UUID): List<WorkoutExerciseEntity>
}