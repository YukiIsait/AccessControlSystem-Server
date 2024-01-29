package tech.youko.acms.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper

@WebFilter
class ResponseCachingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val cachingResponse = response as? ContentCachingResponseWrapper ?: ContentCachingResponseWrapper(response)
        filterChain.doFilter(request, cachingResponse)
        cachingResponse.copyBodyToResponse()
    }
}
