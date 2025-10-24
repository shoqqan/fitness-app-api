package dev.shoqan.fitness_app.workout_exercise

import dev.shoqan.fitness_app.lib.getCurrentUsername
import dev.shoqan.fitness_app.workout.WorkoutService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/workout-exercises")
class WorkoutExerciseController(
    private val workoutExerciseService: WorkoutExerciseService,
    private val workoutService: WorkoutService,
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
        if (!workoutService.existsById(workoutId)) {
            return ResponseEntity.notFound().build()
        }
        val exercises = workoutExerciseService.findByWorkoutId(workoutId)
        return ResponseEntity.ok(exercises.map { it.toResponse() })
    }

    @PostMapping
    fun createWorkoutExercise(
        @Valid @RequestBody request: CreateWorkoutExerciseRequest
    ): ResponseEntity<WorkoutExerciseResponse> {
        val currentUsername = getCurrentUsername()

        val workout = workoutService.findById(request.workoutId)
            ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        // Check if the workout belongs to the current user
        if (workout.user.username != currentUsername) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        val workoutExercise = WorkoutExerciseEntity(
            name = request.name,
            workout = workout,
            restSeconds = request.restSeconds
        )

        val savedWorkoutExercise = workoutExerciseService.save(workoutExercise)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedWorkoutExercise.toResponse())
    }


    //TODO: doesnt worj
    @PatchMapping("/{id}")
    fun updateWorkoutExercise(
        @PathVariable id: UUID,
        @Valid @RequestBody request: UpdateWorkoutExerciseRequest
    ): ResponseEntity<WorkoutExerciseResponse> {
        val currentUsername = getCurrentUsername()
        val existingWorkoutExercise = workoutExerciseService.findById(id)
            ?: return ResponseEntity.notFound().build()

        if (existingWorkoutExercise.workout.user.username != currentUsername) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }


        val updatedWorkoutExercise = workoutExerciseService.save(existingWorkoutExercise)
        return ResponseEntity.ok(updatedWorkoutExercise.toResponse())
    }

    //TODO: doesnt work

    @DeleteMapping("/{id}")
    fun deleteWorkoutExercise(@PathVariable id: UUID): ResponseEntity<Void> {
        val currentUsername = getCurrentUsername()
        val workoutExercise = workoutExerciseService.findById(id)
            ?: return ResponseEntity.notFound().build()

        // Check if the workout belongs to the current user
        if (workoutExercise.workout.user.username != currentUsername) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        workoutExerciseService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    private fun WorkoutExerciseEntity.toResponse(): WorkoutExerciseResponse = WorkoutExerciseResponse(
        id = this.id!!,
        name = this.name,
        workoutId = this.workout.id!!,
        restSeconds = this.restSeconds,
        createdAt = this.createdAt!!,
        updatedAt = this.updatedAt!!
    )
}