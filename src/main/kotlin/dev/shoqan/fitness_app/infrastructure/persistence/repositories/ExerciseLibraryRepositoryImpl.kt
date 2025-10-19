package dev.shoqan.fitness_app.infrastructure.persistence.repositories

import dev.shoqan.fitness_app.domain.repository.ExerciseLibraryRepository
import dev.shoqan.fitness_app.infrastructure.persistence.entity.ExerciseLibraryEntity
import dev.shoqan.fitness_app.infrastructure.persistence.jpa.ExerciseLibraryJpaRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ExerciseLibraryRepositoryImpl(
    private val jpaRepository: ExerciseLibraryJpaRepository
) : ExerciseLibraryRepository {

    override fun findById(id: UUID): ExerciseLibraryEntity? {
        return jpaRepository.findById(id).orElse(null)
    }

    override fun findAll(): List<ExerciseLibraryEntity> {
        return jpaRepository.findAll()
    }

    override fun findByNameIgnoreCase(name: String): ExerciseLibraryEntity? {
        return jpaRepository.findByNameIgnoreCase(name)
    }

    override fun findByNameContainingIgnoreCase(query: String): List<ExerciseLibraryEntity> {
        return jpaRepository.findByNameContainingIgnoreCase(query)
    }

    override fun findByMuscleGroup(muscleGroup: String): List<ExerciseLibraryEntity> {
        return jpaRepository.findByMuscleGroup(muscleGroup)
    }

    override fun findByIsCustomFalse(): List<ExerciseLibraryEntity> {
        return jpaRepository.findByIsCustomFalse()
    }

    override fun findByCreatedById(userId: UUID): List<ExerciseLibraryEntity> {
        return jpaRepository.findByCreatedById(userId)
    }

    override fun searchAvailableExercises(userId: UUID, query: String): List<ExerciseLibraryEntity> {
        return jpaRepository.searchAvailableExercises(userId, query)
    }

    override fun save(exerciseLibrary: ExerciseLibraryEntity): ExerciseLibraryEntity {
        return jpaRepository.save(exerciseLibrary)
    }

    override fun deleteById(id: UUID) {
        jpaRepository.deleteById(id)
    }

    override fun existsById(id: UUID): Boolean {
        return jpaRepository.existsById(id)
    }
}