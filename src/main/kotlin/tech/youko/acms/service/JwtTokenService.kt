package tech.youko.acms.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import tech.youko.acms.properties.JwtProperties
import java.util.UUID
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtTokenService(private val jwtProperties: JwtProperties) : IJwtTokenService {
    // 用于签发 Token 的密钥
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())

    override fun generateToken(authentication: Authentication): String {
        // 令牌
        val id = UUID.randomUUID().toString()
        // 签发时间
        val now = Date()
        // 过期时间
        val expiration = Date(now.time + jwtProperties.duration)
        // 构造 Token
        return Jwts.builder()
            .header()
            .add("typ", "JWT")
            .add("alg", "HS256")
            .and()
            .id(id)
            .issuedAt(now)
            .expiration(expiration)
            .subject(authentication.name)
            .claim(jwtProperties.authoritiesKey, authentication.authorities.joinToString(","))
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    override fun extractAuthentication(token: String): Authentication {
        val claims: Claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
        // 将 Token 中逗号分隔的角色信息转换为 GrantedAuthority 列表
        val authorities: Collection<GrantedAuthority> =
            if (claims.containsKey(jwtProperties.authoritiesKey)) {
                AuthorityUtils.commaSeparatedStringToAuthorityList(claims[jwtProperties.authoritiesKey].toString())
            } else {
                AuthorityUtils.NO_AUTHORITIES
            }
        // 将用户信息封装为 Authentication
        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    override fun validateToken(token: String): Boolean {
        // 如果 Token 能够解析且未过期则返回 True
        return try {
            !Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
                .expiration
                .before(Date())
        } catch (e: Exception) {
            false
        }
    }
}
