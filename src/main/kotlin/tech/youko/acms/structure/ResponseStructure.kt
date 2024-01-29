package tech.youko.acms.structure

import org.springframework.http.HttpStatus

data class ResponseStructure(
    var status: Int = HttpStatus.OK.value(),
    var message: String = HttpStatus.OK.reasonPhrase,
    var data: Any? = null
) {
    companion object {
        fun success(data: Any? = null): ResponseStructure {
            return ResponseStructure(data = data)
        }

        fun success(status: HttpStatus, data: Any? = null): ResponseStructure {
            return ResponseStructure(status.value(), status.reasonPhrase, data)
        }

        fun success(status: Int, message: String, data: Any? = null): ResponseStructure {
            return ResponseStructure(status, message, data)
        }

        fun failure(status: HttpStatus): ResponseStructure {
            return ResponseStructure(status.value(), status.reasonPhrase)
        }

        fun failure(status: Int, message: String): ResponseStructure {
            return ResponseStructure(status, message)
        }
    }
}
