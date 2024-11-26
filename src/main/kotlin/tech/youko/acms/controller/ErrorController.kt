package tech.youko.acms.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.ServletWebRequest
import tech.youko.acms.structure.ResponseStructure

@RestController
class ErrorController(private val errorAttributes: ErrorAttributes) : ErrorController {
    @RequestMapping("\${server.error.path:\${error.path:/error}}")
    @ResponseStatus(HttpStatus.OK)
    fun error(request: HttpServletRequest): ResponseStructure<Any> {
        val attributes = errorAttributes.getErrorAttributes(
            ServletWebRequest(request),
            ErrorAttributeOptions.of(
                ErrorAttributeOptions.Include.STATUS,
                ErrorAttributeOptions.Include.ERROR,
                ErrorAttributeOptions.Include.MESSAGE
            )
        )
        return try {
            ResponseStructure.failure(
                attributes["status"] as Int,
                "${attributes["error"]}: ${attributes["message"]}"
            )
        } catch (_: Exception) {
            ResponseStructure.failure(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
