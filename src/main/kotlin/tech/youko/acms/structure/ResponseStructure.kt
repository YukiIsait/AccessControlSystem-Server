package tech.youko.acms.structure

import org.springframework.http.HttpStatus

data class ResponseStructure<T>(
    var status: Int = HttpStatus.OK.value(),
    var message: String = HttpStatus.OK.reasonPhrase,
    var data: T? = null
) {
    companion object {
        fun <T> success(data: T? = null): ResponseStructure<T> {
            return ResponseStructure(data = data)
        }

        fun <T> success(status: HttpStatus, data: T? = null): ResponseStructure<T> {
            return ResponseStructure(status.value(), status.reasonPhrase, data)
        }

        fun <T> success(status: Int, message: String, data: T? = null): ResponseStructure<T> {
            return ResponseStructure(status, message, data)
        }

        fun failure(status: HttpStatus): ResponseStructure<Any> {
            return ResponseStructure(status.value(), status.reasonPhrase)
        }

        fun failure(status: Int, message: String): ResponseStructure<Any> {
            return ResponseStructure(status, message)
        }
    }
}
