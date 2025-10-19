package dev.shoqan.fitness_app.infrastructure.persistence.jpa

import dev.shoqan.fitness_app.infrastructure.persistence.entity.WorkoutEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface WorkoutJpaRepository: JpaRepository<WorkoutEntity, UUID> {
}