package dev.shoqan.fitness_app.domain.repository

import dev.shoqan.fitness_app.infrastructure.persistence.entity.UserEntity
import java.util.UUID

interface UserRepository {
    fun findById(id: UUID): UserEntity?
    fun findByUsername(username: String): UserEntity?
    fun save(user: UserEntity): UserEntity
    fun existsByUsername(username: String): Boolean
    fun deleteById(id: UUID)
}