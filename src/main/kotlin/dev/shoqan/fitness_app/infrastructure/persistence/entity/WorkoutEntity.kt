package dev.shoqan.fitness_app.infrastructure.persistence.entity

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
data class WorkoutEntity(
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val date: OffsetDateTime,
    @OneToMany(mappedBy = "workout")
    @Column
    val exercizes: MutableList<ExercizeEntity> = mutableListOf(),
    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,
): BaseEntity()