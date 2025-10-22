package dev.shoqan.fitness_app.services

import dev.shoqan.fitness_app.entities.UserEntity
import dev.shoqan.fitness_app.repositories.UserRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserService(
    private val jpaRepository: UserRepository
)  {

     fun findById(id: UUID): UserEntity? {
        return jpaRepository.findById(id).orElse(null)
    }

     fun findByUsername(username: String): UserEntity? {
        return jpaRepository.findByUsername(username)
    }

     fun save(user: UserEntity): UserEntity {
        return jpaRepository.save(user)
    }

     fun existsByUsername(username: String): Boolean {
        return jpaRepository.existsByUsername(username)
    }

     fun deleteById(id: UUID) {
        jpaRepository.deleteById(id)
    }

     fun findAll(): List<UserEntity> {
        return jpaRepository.findAll()
    }

     fun existsById(id: UUID): Boolean {
        return jpaRepository.existsById(id)
    }
}