package dev.shoqan.fitness_app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class FitnessAppApplication

fun main(args: Array<String>) {
	runApplication<FitnessAppApplication>(*args)
}
