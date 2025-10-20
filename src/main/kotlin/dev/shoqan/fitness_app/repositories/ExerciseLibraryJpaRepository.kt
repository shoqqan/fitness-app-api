package dev.shoqan.fitness_app.repositories

import dev.shoqan.fitness_app.entities.ExerciseLibraryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ExerciseLibraryJpaRepository : JpaRepository<ExerciseLibraryEntity, UUID> {
    fun findByNameIgnoreCase(name: String): ExerciseLibraryEntity?
    fun findByNameContainingIgnoreCase(query: String): List<ExerciseLibraryEntity>
    fun findByMuscleGroup(muscleGroup: String): List<ExerciseLibraryEntity>
    fun findByIsCustomFalse(): List<ExerciseLibraryEntity>
    fun findByCreatedById(userId: UUID): List<ExerciseLibraryEntity>

    @Query("""
        SELECT e FROM ExerciseLibraryEntity e
        WHERE (e.isCustom = false OR e.createdBy.id = :userId OR e.isPublic = true)
        AND LOWER(e.name) LIKE LOWER(CONCAT('%', :query, '%'))
        ORDER BY CASE WHEN e.isCustom = false THEN 0 ELSE 1 END, e.name
    """)
    fun searchAvailableExercises(userId: UUID, query: String): List<ExerciseLibraryEntity>
}