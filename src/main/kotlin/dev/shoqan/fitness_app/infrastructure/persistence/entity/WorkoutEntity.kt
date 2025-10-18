package dev.shoqan.fitness_app.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.OffsetDateTime

data class WorkoutEntity(
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val date: OffsetDateTime,
    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,
): BaseEntity()