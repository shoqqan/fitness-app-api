package dev.shoqan.fitness_app.infrastructure.persistence.entity

data class SetEntity(
    val weight: Double,
    val reps: Int
): BaseEntity()