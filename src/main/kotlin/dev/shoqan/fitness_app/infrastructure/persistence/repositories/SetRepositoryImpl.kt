package dev.shoqan.fitness_app.infrastructure.persistence.repositories

import dev.shoqan.fitness_app.domain.repository.SetRepository
import dev.shoqan.fitness_app.infrastructure.persistence.entity.SetEntity
import dev.shoqan.fitness_app.infrastructure.persistence.jpa.SetJpaRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SetRepositoryImpl(
    private val jpaRepository: SetJpaRepository
) : SetRepository {

    override fun findById(id: UUID): SetEntity? {
        return jpaRepository.findById(id).orElse(null)
    }

    override fun findAll(): List<SetEntity> {
        return jpaRepository.findAll()
    }

    override fun findByWorkoutExerciseId(workoutExerciseId: UUID): List<SetEntity> {
        return jpaRepository.findByWorkoutExerciseId(workoutExerciseId)
    }

    override fun findByWorkoutExerciseIdOrderByOrderIndexAsc(workoutExerciseId: UUID): List<SetEntity> {
        return jpaRepository.findByWorkoutExerciseIdOrderByOrderIndexAsc(workoutExerciseId)
    }

    override fun save(set: SetEntity): SetEntity {
        return jpaRepository.save(set)
    }

    override fun deleteById(id: UUID) {
        jpaRepository.deleteById(id)
    }

    override fun existsById(id: UUID): Boolean {
        return jpaRepository.existsById(id)
    }
}