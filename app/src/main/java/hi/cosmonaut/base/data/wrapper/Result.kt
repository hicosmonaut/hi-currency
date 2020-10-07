package hi.cosmonaut.base.data.wrapper

sealed class Result<T : Any> {
    var isHandled: Boolean = false
    data class Success<T>(val data: T) : Result<Any>()
    data class Error<T>(val data: T?, val messageId: Int, val error: Throwable?) : Result<Any>()
    object Loading : Result<Any>()
}