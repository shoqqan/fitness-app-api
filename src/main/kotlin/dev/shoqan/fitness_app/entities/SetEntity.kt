package dev.shoqan.fitness_app.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "sets")
class SetEntity(
    @Column(nullable = false)
    var weight: Double = 0.0,

    @Column(nullable = false)
    var reps: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    var workoutExercise: WorkoutExerciseEntity,

    @Column(nullable = false)
    var orderIndex: Int = 0
) : BaseEntity()