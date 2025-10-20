package dev.shoqan.fitness_app.controllers

import dev.shoqan.fitness_app.dto.CreateWorkoutRequest
import dev.shoqan.fitness_app.dto.UpdateWorkoutRequest
import dev.shoqan.fitness_app.dto.WorkoutResponse
import dev.shoqan.fitness_app.dto.WorkoutSummaryResponse
import dev.shoqan.fitness_app.entities.WorkoutEntity
import dev.shoqan.fitness_app.mappers.toResponse
import dev.shoqan.fitness_app.mappers.toSummaryResponse
import dev.shoqan.fitness_app.services.UserService
import dev.shoqan.fitness_app.services.WorkoutService
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime
import java.util.UUID

@RestController
@RequestMapping("/api/workouts")
class WorkoutController(
    private val workoutService: WorkoutService,
    private val userService: UserService
) {

    @GetMapping
    fun getAllWorkouts(): ResponseEntity<List<WorkoutSummaryResponse>> {
        val workouts = workoutService.findAll()
        return ResponseEntity.ok(workouts.map { it.toSummaryResponse() })
    }

    @GetMapping("/{id}")
    fun getWorkoutById(@PathVariable id: UUID): ResponseEntity<WorkoutResponse> {
        val workout = workoutService.findById(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(workout.toResponse(includeExercises = true))
    }

    @GetMapping("/user/{userId}")
    fun getWorkoutsByUserId(
        @PathVariable userId: UUID,
        @RequestParam(defaultValue = "false") summary: Boolean
    ): ResponseEntity<List<*>> {
        val workouts = workoutService.findByUserIdOrderByDateDesc(userId)

        return if (summary) {
            ResponseEntity.ok(workouts.map { it.toSummaryResponse() })
        } else {
            ResponseEntity.ok(workouts.map { it.toResponse(includeExercises = true) })
        }
    }

    @GetMapping("/user/{userId}/date-range")
    fun getWorkoutsByDateRange(
        @PathVariable userId: UUID,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startDate: OffsetDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) endDate: OffsetDateTime
    ): ResponseEntity<List<WorkoutSummaryResponse>> {
        val workouts = workoutService.findByUserIdAndDateBetween(userId, startDate, endDate)
        return ResponseEntity.ok(workouts.map { it.toSummaryResponse() })
    }

    @PostMapping
    fun createWorkout(@Valid @RequestBody request: CreateWorkoutRequest): ResponseEntity<WorkoutResponse> {
        // Verify user exists
        val user = userService.findById(request.userId)
            ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val workout = WorkoutEntity(
            title = request.title,
            date = request.date,
            user = user
        )

        val savedWorkout = workoutService.save(workout)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedWorkout.toResponse(includeExercises = false))
    }

    @PutMapping("/{id}")
    fun updateWorkout(
        @PathVariable id: UUID,
        @Valid @RequestBody request: UpdateWorkoutRequest
    ): ResponseEntity<WorkoutResponse> {
        val existingWorkout = workoutService.findById(id)
            ?: return ResponseEntity.notFound().build()

        // Update fields if provided
        request.title?.let { existingWorkout.title = it }
        request.date?.let { existingWorkout.date = it }

        val updatedWorkout = workoutService.save(existingWorkout)
        return ResponseEntity.ok(updatedWorkout.toResponse(includeExercises = true))
    }

    @DeleteMapping("/{id}")
    fun deleteWorkout(@PathVariable id: UUID): ResponseEntity<Void> {
        if (!workoutService.existsById(id)) {
            return ResponseEntity.notFound().build()
        }

        workoutService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}/summary")
    fun getWorkoutSummary(@PathVariable id: UUID): ResponseEntity<WorkoutSummaryResponse> {
        val workout = workoutService.findById(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(workout.toSummaryResponse())
    }
}