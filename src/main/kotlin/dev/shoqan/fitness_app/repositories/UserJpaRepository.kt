package dev.shoqan.fitness_app.repositories

import dev.shoqan.fitness_app.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, UUID> {
    fun findByUsername(username: String): UserEntity?
    fun existsByUsername(username: String): Boolean
}