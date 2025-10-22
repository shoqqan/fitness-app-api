package dev.shoqan.fitness_app.extensions

import dev.shoqan.fitness_app.user.UserEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User

/**
 * Получает username текущего аутентифицированного пользователя из SecurityContext
 */
fun getCurrentUsername(): String {
    val authentication = SecurityContextHolder.getContext().authentication
    return when (val principal = authentication.principal) {
        is User -> principal.username
        is String -> principal
        else -> throw IllegalStateException("Unable to extract username from authentication")
    }


}

/**
 * Проверяет, принадлежит ли ресурс текущему пользователю
 */
fun isOwner(resourceUser: UserEntity): Boolean {
    val currentUsername = getCurrentUsername()
    return resourceUser.username == currentUsername
}