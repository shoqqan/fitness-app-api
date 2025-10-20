package dev.shoqan.fitness_app.controllers

import dev.shoqan.fitness_app.dto.CreateSetRequest
import dev.shoqan.fitness_app.dto.SetResponse
import dev.shoqan.fitness_app.dto.UpdateSetRequest
import dev.shoqan.fitness_app.entities.SetEntity
import dev.shoqan.fitness_app.mappers.toResponse
import dev.shoqan.fitness_app.services.SetService
import dev.shoqan.fitness_app.services.WorkoutExerciseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/sets")
class SetController(
    private val setService: SetService,
    private val workoutExerciseService: WorkoutExerciseService
) {

    @GetMapping("/{id}")
    fun getSetById(@PathVariable id: UUID): ResponseEntity<SetResponse> {
        val set = setService.findById(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(set.toResponse())
    }

    @GetMapping("/workout-exercise/{workoutExerciseId}")
    fun getSetsByWorkoutExerciseId(
        @PathVariable workoutExerciseId: UUID
    ): ResponseEntity<List<SetResponse>> {
        val sets = setService.findByWorkoutExerciseIdOrderByOrderIndexAsc(workoutExerciseId)
        return ResponseEntity.ok(sets.map { it.toResponse() })
    }

    @PostMapping
    fun createSet(@Valid @RequestBody request: CreateSetRequest): ResponseEntity<SetResponse> {
        // Verify workout exercise exists
        val workoutExercise = workoutExerciseService.findById(request.workoutExerciseId)
            ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val set = SetEntity(
            workoutExercise = workoutExercise,
            weight = request.weight,
            reps = request.reps,
            orderIndex = request.orderIndex
        )

        val savedSet = setService.save(set)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSet.toResponse())
    }

    @PostMapping("/bulk")
    fun createMultipleSets(
        @Valid @RequestBody requests: List<CreateSetRequest>
    ): ResponseEntity<List<SetResponse>> {
        val sets = requests.mapIndexed { index, request ->
            // Verify workout exercise exists
            val workoutExercise = workoutExerciseService.findById(request.workoutExerciseId)
                ?: throw IllegalArgumentException("Workout exercise not found: ${request.workoutExerciseId}")

            SetEntity(
                workoutExercise = workoutExercise,
                weight = request.weight,
                reps = request.reps,
                orderIndex = request.orderIndex.takeIf { it >= 0 } ?: index
            )
        }

        val savedSets = sets.map { setService.save(it) }
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedSets.map { it.toResponse() })
    }

    @PutMapping("/{id}")
    fun updateSet(
        @PathVariable id: UUID,
        @Valid @RequestBody request: UpdateSetRequest
    ): ResponseEntity<SetResponse> {
        val existingSet = setService.findById(id)
            ?: return ResponseEntity.notFound().build()

        // Update fields if provided
        request.weight?.let { existingSet.weight = it }
        request.reps?.let { existingSet.reps = it }

        val updatedSet = setService.save(existingSet)
        return ResponseEntity.ok(updatedSet.toResponse())
    }

    @DeleteMapping("/{id}")
    fun deleteSet(@PathVariable id: UUID): ResponseEntity<Void> {
        if (!setService.existsById(id)) {
            return ResponseEntity.notFound().build()
        }

        setService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/workout-exercise/{workoutExerciseId}")
    fun deleteAllSetsForWorkoutExercise(
        @PathVariable workoutExerciseId: UUID
    ): ResponseEntity<Map<String, Int>> {
        val sets = setService.findByWorkoutExerciseId(workoutExerciseId)
        var deletedCount = 0

        sets.forEach { set ->
            setService.deleteById(set.id!!)
            deletedCount++
        }

        return ResponseEntity.ok(mapOf("deletedCount" to deletedCount))
    }
}