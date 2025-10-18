package dev.shoqan.fitness_app.infrastructure.persistence.entity
data class ExercizeEntity(
    val totalWeight: Double,
    val totalReps: Int,
    val restMinutes: Int
): BaseEntity()