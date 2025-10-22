package dev.shoqan.fitness_app.controllers

import dev.shoqan.fitness_app.dto.CreateExerciseLibraryRequest
import dev.shoqan.fitness_app.dto.ExerciseLibraryResponse
import dev.shoqan.fitness_app.entities.ExerciseLibraryEntity
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






    @PostMapping
    fun createExercise(
        @Valid @RequestBody request: CreateExerciseLibraryRequest
    ): ResponseEntity<ExerciseLibraryResponse> {
        // Verify user exists if createdById is provided
        val createdByUser = request.createdById?.let { userId ->
            userService.findById(userId)
                ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

        val exercise = ExerciseLibraryEntity(
            name = request.name,
            description = request.description,
            muscleGroup = request.muscleGroup,
            isCustom = request.createdById != null,
            isPublic = request.isPublic,
            createdBy = createdByUser
        )

        val savedExercise = exerciseLibraryService.save(exercise)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExercise.toResponse())
    }

    @DeleteMapping("/{id}")
    fun deleteExercise(@PathVariable id: UUID): ResponseEntity<Void> {
        if (!exerciseLibraryService.existsById(id)) {
            return ResponseEntity.notFound().build()
        }

        exerciseLibraryService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    fun ExerciseLibraryEntity.toResponse(): ExerciseLibraryResponse  = ExerciseLibraryResponse(
            id = this.id,
            name = this.name,
            description = this.description,
            muscleGroup = this.muscleGroup,
            isCustom = this.isCustom,
            isPublic = this.isPublic,
            createdById = this.createdBy?.id,
            createdAt = this.createdAt!!,
            updatedAt = this.updatedAt!!
        )

}