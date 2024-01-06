package tech.youko.acms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AcmsApplication

fun main(args: Array<String>) {
    runApplication<AcmsApplication>(*args)
}
