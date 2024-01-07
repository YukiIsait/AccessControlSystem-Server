package tech.youko.acms.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import tech.youko.acms.filter.JwtTokenAuthorizationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class WebSecurityConfiguration {
    @Bean
    fun securityFilterChain(
        httpSecurity: HttpSecurity,
        corsConfigurationSource: CorsConfigurationSource,
        authenticationManager: AuthenticationManager,
        jwtTokenAuthorizationFilter: JwtTokenAuthorizationFilter
    ): SecurityFilterChain {
        return httpSecurity
            .httpBasic { configurer ->
                configurer.disable()
            } // 关闭基本身份验证
            .csrf { configurer ->
                configurer.disable()
            } // 关闭跨域保护
            .sessionManagement { configurer ->
                configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            } // 关闭 Session 管理
            .cors { configurer ->
                configurer.configurationSource(corsConfigurationSource)
            } // 配置跨域
            .addFilterBefore(
                jwtTokenAuthorizationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            ) // 添加 JWT Token 过滤器
            .authenticationManager(authenticationManager) // 配置认证管理器
            .authorizeHttpRequests { registry ->
                registry
                    .requestMatchers(HttpMethod.GET).denyAll()
                    .anyRequest().permitAll()
            } // 配置授权规则, 禁用所有 GET 请求
//            .exceptionHandling { configurer ->
//                configurer
//                    .authenticationEntryPoint { _, response, _ ->
//                        response.status = HttpStatus.UNAUTHORIZED.value()
//                        response.writer.write(
//                            JsonUtil.toJson(
//                                CommonResultStructure.failed("401: Full authentication is required to access this resource")
//                            )
//                        )
//                    }
//                    .accessDeniedHandler { _, response, _ ->
//                        response.status = HttpStatus.FORBIDDEN.value()
//                        response.writer.write(
//                            JsonUtil.toJson(
//                                CommonResultStructure.failed("403: Access is denied")
//                            )
//                        )
//                    }
//            } // 配置异常处理
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        // 允许所有跨域访问
        configuration.addAllowedOrigin(CorsConfiguration.ALL)
        configuration.addAllowedMethod(CorsConfiguration.ALL)
        configuration.addAllowedHeader(CorsConfiguration.ALL)
        return CorsConfigurationSource { configuration }
    }

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
