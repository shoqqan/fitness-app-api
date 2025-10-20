package dev.shoqan.fitness_app.controllers

import dev.shoqan.fitness_app.dto.CreateExerciseLibraryRequest
import dev.shoqan.fitness_app.dto.ExerciseLibraryResponse
import dev.shoqan.fitness_app.dto.UpdateExerciseLibraryRequest
import dev.shoqan.fitness_app.entities.ExerciseLibraryEntity
import dev.shoqan.fitness_app.mappers.toResponse
import dev.shoqan.fitness_app.services.ExerciseLibraryService
import dev.shoqan.fitness_app.services.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/exercises")
class ExerciseLibraryController(
    private val exerciseLibraryService: ExerciseLibraryService,
    private val userService: UserService
) {

    @GetMapping
    fun getAllExercises(): ResponseEntity<List<ExerciseLibraryResponse>> {
        val exercises = exerciseLibraryService.findAll()
        return ResponseEntity.ok(exercises.map { it.toResponse() })
    }

    @GetMapping("/{id}")
    fun getExerciseById(@PathVariable id: UUID): ResponseEntity<ExerciseLibraryResponse> {
        val exercise = exerciseLibraryService.findById(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(exercise.toResponse())
    }

    @GetMapping("/search")
    fun searchExercises(
        @RequestParam(required = true) userId: UUID,
        @RequestParam(defaultValue = "") query: String
    ): ResponseEntity<List<ExerciseLibraryResponse>> {
        val exercises = exerciseLibraryService.searchAvailableExercises(userId, query)
        return ResponseEntity.ok(exercises.map { it.toResponse() })
    }

    @GetMapping("/muscle-group/{muscleGroup}")
    fun getExercisesByMuscleGroup(
        @PathVariable muscleGroup: String
    ): ResponseEntity<List<ExerciseLibraryResponse>> {
        val exercises = exerciseLibraryService.findByMuscleGroup(muscleGroup)
        return ResponseEntity.ok(exercises.map { it.toResponse() })
    }

    @GetMapping("/built-in")
    fun getBuiltInExercises(): ResponseEntity<List<ExerciseLibraryResponse>> {
        val exercises = exerciseLibraryService.findByIsCustomFalse()
        return ResponseEntity.ok(exercises.map { it.toResponse() })
    }

    @GetMapping("/user/{userId}/custom")
    fun getUserCustomExercises(
        @PathVariable userId: UUID
    ): ResponseEntity<List<ExerciseLibraryResponse>> {
        val exercises = exerciseLibraryService.findByCreatedById(userId)
        return ResponseEntity.ok(exercises.map { it.toResponse() })
    }

    @GetMapping("/name/{name}")
    fun getExerciseByName(@PathVariable name: String): ResponseEntity<ExerciseLibraryResponse> {
        val exercise = exerciseLibraryService.findByNameIgnoreCase(name)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(exercise.toResponse())
    }

    @PostMapping
    fun createExercise(
        @Valid @RequestBody request: CreateExerciseLibraryRequest
    ): ResponseEntity<ExerciseLibraryResponse> {
        // Check if exercise with this name already exists
        val existing = exerciseLibraryService.findByNameIgnoreCase(request.name)
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }

        // Verify user exists if createdById is provided
        val createdByUser = request.createdById?.let { userId ->
            userService.findById(userId)
                ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

        val exercise = ExerciseLibraryEntity(
            name = request.name,
            description = request.description,
            muscleGroup = request.muscleGroup,
            category = request.category,
            isCustom = request.createdById != null,
            isPublic = request.isPublic,
            createdBy = createdByUser
        )

        val savedExercise = exerciseLibraryService.save(exercise)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExercise.toResponse())
    }

    @PutMapping("/{id}")
    fun updateExercise(
        @PathVariable id: UUID,
        @Valid @RequestBody request: UpdateExerciseLibraryRequest
    ): ResponseEntity<ExerciseLibraryResponse> {
        val existingExercise = exerciseLibraryService.findById(id)
            ?: return ResponseEntity.notFound().build()

        // Check if new name conflicts with another exercise
        request.name?.let { newName ->
            val exerciseWithName = exerciseLibraryService.findByNameIgnoreCase(newName)
            if (exerciseWithName != null && exerciseWithName.id != id) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build()
            }
        }

        // Update fields if provided
        request.name?.let { existingExercise.name = it }
        request.description?.let { existingExercise.description = it }
        request.muscleGroup?.let { existingExercise.muscleGroup = it }
        request.category?.let { existingExercise.category = it }
        request.isPublic?.let { existingExercise.isPublic = it }

        val updatedExercise = exerciseLibraryService.save(existingExercise)
        return ResponseEntity.ok(updatedExercise.toResponse())
    }

    @DeleteMapping("/{id}")
    fun deleteExercise(@PathVariable id: UUID): ResponseEntity<Void> {
        if (!exerciseLibraryService.existsById(id)) {
            return ResponseEntity.notFound().build()
        }

        exerciseLibraryService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}