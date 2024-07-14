package uk.co.joeshuff.immichframe.util

enum class Status {
    SUCCESS,
    ERROR
}

data class ImmichResult<out T>(
    val status: Status,
    val data: T?,
    val message:String?
){
    companion object{

        fun <T> success(data:T? = null): ImmichResult<T> {
            return ImmichResult(Status.SUCCESS, data, null)
        }

        fun <T> error(msg:String? = null, data:T? = null): ImmichResult<T> {
            return ImmichResult(Status.ERROR, data, msg)
        }
    }
}