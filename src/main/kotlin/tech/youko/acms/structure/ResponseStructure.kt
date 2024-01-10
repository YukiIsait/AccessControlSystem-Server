package tech.youko.acms.structure

data class ResponseStructure(
    var status: Int = Status.SUCCESS.ordinal,
    var message: String = Status.SUCCESS.name,
    var data: Any? = null
) {
    enum class Status {
        SUCCESS,
        FAILURE
    }

    companion object {
        fun success(data: Any? = null): ResponseStructure {
            return ResponseStructure(data = data)
        }

        fun failure(message: String = Status.FAILURE.name): ResponseStructure {
            return ResponseStructure(-Status.FAILURE.ordinal, message)
        }
    }
}
