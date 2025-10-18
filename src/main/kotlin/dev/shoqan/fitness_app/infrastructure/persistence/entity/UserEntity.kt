package dev.shoqan.fitness_app.infrastructure.persistence.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class UserEntity (
    @Column(unique = true, nullable = false)
    val username: String,
    val password: String,
    @OneToMany(mappedBy="user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY )
    val workouts: MutableList<WorkoutEntity> = mutableListOf(),
): BaseEntity()