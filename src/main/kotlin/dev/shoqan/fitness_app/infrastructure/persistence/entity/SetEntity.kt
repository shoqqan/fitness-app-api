package dev.shoqan.fitness_app.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "sets")
data class SetEntity(
    @Column(nullable = false)
    val weight: Double = 0.0,
    @Column(nullable = false)
    val reps: Int = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercize_id", nullable = false)
    var exercize: ExercizeEntity,
): BaseEntity()