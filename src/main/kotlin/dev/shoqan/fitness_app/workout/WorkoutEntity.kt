package dev.shoqan.fitness_app.workout

import dev.shoqan.fitness_app.shared.BaseEntity
import dev.shoqan.fitness_app.user.UserEntity
import dev.shoqan.fitness_app.workout_exercise.WorkoutExerciseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "workouts")
class WorkoutEntity(
    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var date: OffsetDateTime,

    @OneToMany(mappedBy = "workout", cascade = [CascadeType.ALL], orphanRemoval = true)
    var exercises: MutableList<WorkoutExerciseEntity> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity
) : BaseEntity()