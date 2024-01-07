package tech.youko.acms.controller.advice

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import tech.youko.acms.structure.ResponseStructure

@RestControllerAdvice
class ExceptionAdvice {
    @ExceptionHandler
    fun handleException(e: Exception): ResponseStructure {
        return ResponseStructure.failure("${e.javaClass.simpleName}: ${e.message}")
    }
}
