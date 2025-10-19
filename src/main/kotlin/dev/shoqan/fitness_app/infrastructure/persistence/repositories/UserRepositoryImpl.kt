package dev.shoqan.fitness_app.infrastructure.persistence.repositories

import dev.shoqan.fitness_app.domain.repository.UserRepository
import dev.shoqan.fitness_app.infrastructure.persistence.entity.UserEntity
import dev.shoqan.fitness_app.infrastructure.persistence.jpa.UserJpaRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserRepositoryImpl(
    private val jpaRepository: UserJpaRepository
) : UserRepository {

    override fun findById(id: UUID): UserEntity? {
        return jpaRepository.findById(id).orElse(null)
    }

    override fun findByUsername(username: String): UserEntity? {
        return jpaRepository.findByUsername(username)
    }

    override fun save(user: UserEntity): UserEntity {
        return jpaRepository.save(user)
    }

    override fun existsByUsername(username: String): Boolean {
        return jpaRepository.existsByUsername(username)
    }

    override fun deleteById(id: UUID) {
        jpaRepository.deleteById(id)
    }
}