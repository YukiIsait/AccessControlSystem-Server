package tech.youko.acms.configuration

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.youko.acms.filter.ResponseCachingFilter

@Configuration
class WebConfiguration {
    @Bean
    fun responseCachingFilterRegistration(): FilterRegistrationBean<ResponseCachingFilter> {
        return FilterRegistrationBean<ResponseCachingFilter>(ResponseCachingFilter())
    }
}
