package dev.shoqan.fitness_app.services

import dev.shoqan.fitness_app.entities.ExerciseLibraryEntity
import dev.shoqan.fitness_app.repositories.ExerciseLibraryJpaRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ExerciseLibraryService(
    private val jpaRepository: ExerciseLibraryJpaRepository
)  {

    fun findById(id: UUID): ExerciseLibraryEntity? {
        return jpaRepository.findById(id).orElse(null)
    }

    fun findAll(): List<ExerciseLibraryEntity> {
        return jpaRepository.findAll()
    }

    fun searchAvailableExercises(userId: UUID, query: String): List<ExerciseLibraryEntity> {
        return jpaRepository.searchAvailableExercises(userId, query)
    }

    fun save(exerciseLibrary: ExerciseLibraryEntity): ExerciseLibraryEntity {
        return jpaRepository.save(exerciseLibrary)
    }

    fun deleteById(id: UUID) {
        jpaRepository.deleteById(id)
    }

    fun existsById(id: UUID): Boolean {
        return jpaRepository.existsById(id)
    }
}