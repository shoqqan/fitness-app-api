package dev.shoqan.fitness_app.set

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SetJpaRepository : JpaRepository<SetEntity, UUID> {
    fun findByWorkoutExerciseId(workoutExerciseId: UUID): List<SetEntity>
    fun findByWorkoutExerciseIdOrderByOrderIndexAsc(workoutExerciseId: UUID): List<SetEntity>
}