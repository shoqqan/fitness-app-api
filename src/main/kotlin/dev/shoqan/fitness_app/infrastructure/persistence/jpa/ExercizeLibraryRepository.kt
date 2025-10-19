package dev.shoqan.fitness_app.infrastructure.persistence.jpa

import dev.shoqan.fitness_app.infrastructure.persistence.entity.ExerciseLibraryEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ExercizeLibraryRepository: JpaRepository<ExerciseLibraryEntity, UUID> {
}