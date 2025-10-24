package dev.shoqan.fitness_app.auth

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.OffsetDateTime

data class ErrorResponse(
    val error: String,
    val message: String,
    val timestamp: String = OffsetDateTime.now().toString()
)

@Component
class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        // Skip JWT validation for public endpoints
        val path = request.requestURI
        if (path.startsWith("/api/auth/") ||
            path.startsWith("/v3/api-docs") ||
            path.startsWith("/swagger-ui")
        ) {
            filterChain.doFilter(request, response)
            return
        }

        val header = request.getHeader("Authorization")

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val token = header.substring(7)

            if (jwtUtil.validateToken(token)) {
                val username = jwtUtil.getUsernameFromToken(token)
                val userDetails = userDetailsService.loadUserByUsername(username)

                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }

            filterChain.doFilter(request, response)

        } catch (e: ExpiredJwtException) {
            handleException(
                response,
                ErrorResponse(
                    error = "TOKEN_EXPIRED",
                    message = "Your session has expired. Please login again."
                )
            )
        } catch (e: MalformedJwtException) {
            handleException(
                response,
                ErrorResponse(
                    error = "INVALID_TOKEN",
                    message = "JWT token is malformed or invalid."
                )
            )
        } catch (e: SignatureException) {
            handleException(
                response,
                ErrorResponse(
                    error = "INVALID_SIGNATURE",
                    message = "JWT signature is invalid."
                )
            )
        } catch (e: Exception) {
            handleException(
                response,
                ErrorResponse(
                    error = "AUTHENTICATION_FAILED",
                    message = "Authentication failed: ${e.message}"
                )
            )
        }
    }

    private fun handleException(response: HttpServletResponse, errorResponse: ErrorResponse) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}