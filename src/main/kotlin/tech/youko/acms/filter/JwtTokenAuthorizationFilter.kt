package tech.youko.acms.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import tech.youko.acms.helper.JwtHelper

@Component
class JwtTokenAuthorizationFilter(private val jwtHelper: JwtHelper) : OncePerRequestFilter() {
    companion object {
        private const val HEADER_PREFIX = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 从请求头中获取 Token
        val bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION)
        // 没有 Token, 且不是以 Bearer 开头则放行至下一个过滤器
        if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith(HEADER_PREFIX, true)) {
            // 裁剪掉前缀 Bearer, 保留 Token
            val token = bearerToken.substring(HEADER_PREFIX.length)
            // 验证 Token 是否合法
            if (jwtHelper.validateToken(token)) {
                // 合法则将 Token 中的用户信息放入 SecurityContext
                val authentication = jwtHelper.extractAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        // 如果请求头中没有携带 Token 或 Token 无效则放行至下一个过滤器
        filterChain.doFilter(request, response)
    }
}
