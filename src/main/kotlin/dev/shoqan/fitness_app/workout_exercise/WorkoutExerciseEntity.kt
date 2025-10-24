package dev.shoqan.fitness_app.workout_exercise

import dev.shoqan.fitness_app.shared.BaseEntity
import dev.shoqan.fitness_app.set.SetEntity
import dev.shoqan.fitness_app.workout.WorkoutEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "workout_exercises")
class WorkoutExerciseEntity(
    @Column(nullable = false)
    var name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    var workout: WorkoutEntity,

    @OneToMany(mappedBy = "workoutExercise", cascade = [CascadeType.ALL], orphanRemoval = true)
    var sets: MutableList<SetEntity> = mutableListOf(),

    @Column(nullable = false)
    var restSeconds: Int = 90,
) : BaseEntity() {

    fun getTotalVolume(): Double = sets.sumOf { it.weight * it.reps }

    fun getTotalReps(): Int = sets.sumOf { it.reps }
}