package dev.shoqan.fitness_app.controllers

import dev.shoqan.fitness_app.dto.CreateWorkoutExerciseRequest
import dev.shoqan.fitness_app.dto.UpdateWorkoutExerciseRequest
import dev.shoqan.fitness_app.dto.WorkoutExerciseResponse
import dev.shoqan.fitness_app.entities.WorkoutExerciseEntity
import dev.shoqan.fitness_app.mappers.toResponse
import dev.shoqan.fitness_app.services.ExerciseLibraryService
import dev.shoqan.fitness_app.services.WorkoutExerciseService
import dev.shoqan.fitness_app.services.WorkoutService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/workout-exercises")
class WorkoutExerciseController(
    private val workoutExerciseService: WorkoutExerciseService,
    private val workoutService: WorkoutService,
    private val exerciseLibraryService: ExerciseLibraryService
) {

    @GetMapping("/{id}")
    fun getWorkoutExerciseById(@PathVariable id: UUID): ResponseEntity<WorkoutExerciseResponse> {
        val workoutExercise = workoutExerciseService.findById(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(workoutExercise.toResponse())
    }

    @GetMapping("/workout/{workoutId}")
    fun getWorkoutExercisesByWorkoutId(
        @PathVariable workoutId: UUID
    ): ResponseEntity<List<WorkoutExerciseResponse>> {
        val exercises = workoutExerciseService.findByWorkoutIdOrderByOrderIndexAsc(workoutId)
        return ResponseEntity.ok(exercises.map { it.toResponse() })
    }

    @GetMapping("/exercise-library/{exerciseLibraryId}")
    fun getWorkoutExercisesByExerciseLibraryId(
        @PathVariable exerciseLibraryId: UUID
    ): ResponseEntity<List<WorkoutExerciseResponse>> {
        val exercises = workoutExerciseService.findByExerciseLibraryId(exerciseLibraryId)
        return ResponseEntity.ok(exercises.map { it.toResponse() })
    }

    @PostMapping
    fun createWorkoutExercise(
        @Valid @RequestBody request: CreateWorkoutExerciseRequest
    ): ResponseEntity<WorkoutExerciseResponse> {
        // Verify workout exists
        val workout = workoutService.findById(request.workoutId)
            ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        // Verify exercise library entry exists
        val exerciseLibrary = exerciseLibraryService.findById(request.exerciseLibraryId)
            ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val workoutExercise = WorkoutExerciseEntity(
            workout = workout,
            exerciseLibrary = exerciseLibrary,
            restSeconds = request.restSeconds,
            orderIndex = request.orderIndex
        )

        val savedWorkoutExercise = workoutExerciseService.save(workoutExercise)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedWorkoutExercise.toResponse())
    }

    @PutMapping("/{id}")
    fun updateWorkoutExercise(
        @PathVariable id: UUID,
        @Valid @RequestBody request: UpdateWorkoutExerciseRequest
    ): ResponseEntity<WorkoutExerciseResponse> {
        val existingWorkoutExercise = workoutExerciseService.findById(id)
            ?: return ResponseEntity.notFound().build()

        // Update fields if provided
        request.restSeconds?.let { existingWorkoutExercise.restSeconds = it }
        request.orderIndex?.let { existingWorkoutExercise.orderIndex = it }

        val updatedWorkoutExercise = workoutExerciseService.save(existingWorkoutExercise)
        return ResponseEntity.ok(updatedWorkoutExercise.toResponse())
    }

    @DeleteMapping("/{id}")
    fun deleteWorkoutExercise(@PathVariable id: UUID): ResponseEntity<Void> {
        if (!workoutExerciseService.existsById(id)) {
            return ResponseEntity.notFound().build()
        }

        workoutExerciseService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}/metrics")
    fun getWorkoutExerciseMetrics(@PathVariable id: UUID): ResponseEntity<Map<String, Any>> {
        val workoutExercise = workoutExerciseService.findById(id)
            ?: return ResponseEntity.notFound().build()

        val metrics = mapOf(
            "totalVolume" to workoutExercise.getTotalVolume(),
            "totalReps" to workoutExercise.getTotalReps(),
            "setCount" to workoutExercise.sets.size
        )

        return ResponseEntity.ok(metrics)
    }
}