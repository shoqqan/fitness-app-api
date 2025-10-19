package dev.shoqan.fitness_app.infrastructure.persistence.jpa

import dev.shoqan.fitness_app.infrastructure.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserJpaRepository: JpaRepository<UserEntity, UUID> {
}