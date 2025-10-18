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
@Table(name = "exercizes")
data class ExercizeEntity(
    @Column(nullable = false)
    val totalWeight: Double,
    @Column(nullable = false)
    val totalReps: Int,
    @Column(nullable = false)
    val restMinutes: Int,
    @OneToMany(mappedBy="exercize", cascade = [CascadeType.ALL])
    var sets: MutableList<SetEntity> = mutableListOf(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    var workout: WorkoutEntity,
): BaseEntity()