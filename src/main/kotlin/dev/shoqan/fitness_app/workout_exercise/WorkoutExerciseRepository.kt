package dev.shoqan.fitness_app.workout_exercise

import dev.shoqan.fitness_app.workout_exercise.WorkoutExerciseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface WorkoutExerciseRepository : JpaRepository<WorkoutExerciseEntity, UUID> {
    fun findByWorkoutId(workoutId: UUID): List<WorkoutExerciseEntity>
}