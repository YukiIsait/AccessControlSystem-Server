package tech.youko.acms.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "acms.jwt")
data class JwtProperties(
    var secretKey: String = "49A4A069-5A4C-4756-B573-E71B1411C3D9",
    var duration: Long = 3600000
)
