package dev.shoqan.fitness_app.infrastructure.persistence.entity

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    var workout: WorkoutEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_library_id", nullable = false)
    var exerciseLibrary: ExerciseLibraryEntity,

    @OneToMany(mappedBy = "workoutExercise", cascade = [CascadeType.ALL], orphanRemoval = true)
    var sets: MutableList<SetEntity> = mutableListOf(),

    @Column(nullable = false)
    var restSeconds: Int = 60,

    @Column(nullable = false)
    var orderIndex: Int = 0
) : BaseEntity() {

    fun getTotalVolume(): Double = sets.sumOf { it.weight * it.reps }

    fun getTotalReps(): Int = sets.sumOf { it.reps }
}