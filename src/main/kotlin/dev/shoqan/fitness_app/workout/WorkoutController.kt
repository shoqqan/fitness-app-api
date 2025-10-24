package dev.shoqan.fitness_app.workout

import dev.shoqan.fitness_app.lib.getCurrentUsername
import dev.shoqan.fitness_app.user.UserService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime
import java.util.UUID

@RestController
@RequestMapping("/api/workouts")
@Tag(name = "Workouts", description = "API для управления тренировками")
@SecurityRequirement(name = "bearerAuth")
class WorkoutController(
    private val workoutService: WorkoutService,
    private val userService: UserService
) {



    @GetMapping
    fun getWorkouts(): ResponseEntity<List<WorkoutResponse>> {
        val currentUsername = getCurrentUsername()
        val user = userService.findByUsername(currentUsername)
            ?: throw IllegalStateException("Authenticated user not found: $currentUsername")

        val workouts = workoutService.findByUserId(user.id!!)
        return ResponseEntity.ok(workouts.map { it.toResponse() })
    }
//    @GetMapping
//    fun getAllWorkouts() {
//        val workouts = workoutService.findAll()
//        return ResponseEntity.ok(workouts.map { it.toSummaryResponse() })
//    }

    @GetMapping("/{id}")
    fun getWorkoutById(@PathVariable id: UUID): ResponseEntity<WorkoutResponse> {
        val workout = workoutService.findById(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(workout.toResponse())
    }


    @PostMapping
    fun createWorkout(@Valid @RequestBody request: CreateWorkoutRequest): ResponseEntity<WorkoutResponse> {
        val currentUsername = getCurrentUsername()
        val user = userService.findByUsername(currentUsername)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val workout = WorkoutEntity(
            title = request.title,
            date = OffsetDateTime.now(),
            user = user
        )

        val savedWorkout = workoutService.save(workout)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedWorkout.toResponse())
    }

    @PatchMapping("/{id}")
    fun updateWorkout(
        @PathVariable id: UUID,
        @Valid @RequestBody request: UpdateWorkoutRequest
    ): ResponseEntity<WorkoutResponse> {
        val currentUsername = getCurrentUsername()
        val existingWorkout = workoutService.findById(id)
            ?: return ResponseEntity.notFound().build()

        // Check if the workout belongs to the current user
        if (existingWorkout.user.username != currentUsername) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        // Update fields if provided
        request.title?.let { existingWorkout.title = it }
        request.date?.let { existingWorkout.date = it }

        val updatedWorkout = workoutService.save(existingWorkout)
        return ResponseEntity.ok(updatedWorkout.toResponse())
    }

    @DeleteMapping("/{id}")
    fun deleteWorkout(@PathVariable id: UUID): ResponseEntity<Void> {
        val currentUsername = getCurrentUsername()
        val workout = workoutService.findById(id)
            ?: return ResponseEntity.notFound().build()

        // Check if the workout belongs to the current user
        if (workout.user.username != currentUsername) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        workoutService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    private fun WorkoutEntity.toResponse(): WorkoutResponse = WorkoutResponse(
        id = this.id!!,
        title = this.title,
        date = this.date,
        userId = this.user.id!!,
        createdAt = this.createdAt!!,
        updatedAt = this.updatedAt!!
    )
}