package dev.shoqan.fitness_app.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "exercise_library")
class ExerciseLibraryEntity(
    @Column(nullable = false)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column
    var muscleGroup: String? = null,

    @Column
    var category: String? = null,

    @Column(nullable = false)
    var isCustom: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    var createdBy: UserEntity? = null,

    @Column(nullable = false)
    var isPublic: Boolean = false
) : BaseEntity()
