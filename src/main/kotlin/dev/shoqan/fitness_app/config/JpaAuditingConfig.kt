package dev.shoqan.fitness_app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import java.time.OffsetDateTime
import java.time.temporal.TemporalAccessor
import java.util.Optional

@Configuration
class JpaAuditingConfig {

    @Bean
    fun dateTimeProvider(): DateTimeProvider {
        return DateTimeProvider { Optional.of<TemporalAccessor>(OffsetDateTime.now()) }
    }
}