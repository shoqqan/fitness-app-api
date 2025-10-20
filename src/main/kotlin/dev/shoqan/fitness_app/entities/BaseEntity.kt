package dev.shoqan.fitness_app.entities

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime
import java.util.UUID

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    open val id: UUID = UUID.randomUUID();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    open var createdAt: OffsetDateTime = OffsetDateTime.now()

    @LastModifiedDate
    @Column(nullable = false)
    open var updatedAt: OffsetDateTime = OffsetDateTime.now()
}