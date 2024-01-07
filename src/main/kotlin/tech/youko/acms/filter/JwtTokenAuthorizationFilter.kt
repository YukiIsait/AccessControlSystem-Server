package tech.youko.acms.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import tech.youko.acms.service.JwtTokenService

@Component
class JwtTokenAuthorizationFilter(private val jwtTokenService: JwtTokenService) : GenericFilterBean() {
    companion object {
        const val HEADER_PREFIX = "Bearer "
    }

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        // 从请求头中获取 Token
        val bearerToken = (request as HttpServletRequest).getHeader(HttpHeaders.AUTHORIZATION)
        // 判断是否有 Token, 且是否以 Bearer 开头
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            // 裁剪掉前缀 Bearer, 保留 Token
            val token = bearerToken.substring(HEADER_PREFIX.length)
            // 验证 Token 是否合法
            if (jwtTokenService.validateToken(token)) {
                // 合法则将 Token 中的用户信息放入 SecurityContext
                val authentication = jwtTokenService.extractAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                // 不合法则拦截返回 401
                (response as HttpServletResponse).status = HttpStatus.UNAUTHORIZED.value()
                return
            }
        }
        // 如果请求头中没有携带 Token 则放行至下一个过滤器
        chain.doFilter(request, response)
    }
}
