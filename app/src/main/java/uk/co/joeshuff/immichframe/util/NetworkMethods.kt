package uk.co.joeshuff.immichframe.util

import android.util.Log
import okhttp3.Headers
import retrofit2.Response
import java.net.HttpURLConnection

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val payload: T, val headers: Map<String, String> = emptyMap()) :
        ApiResult<T>()

    sealed class Error(open val exception: Exception) : ApiResult<Nothing>() {
        data class NetworkError(override val exception: Exception) : Error(exception)
        data object NoContent : Error(Exception("No content"))
    }
}

class NetworkError(message: String?, cause: Throwable?) : Exception(message, cause)

data class ApiError(val code: Int, override val message: String?) : Exception(message)

class NullBodyError : Exception("API returned a null body")

suspend inline fun <reified T : Any> makeApiRequest(
    logCall: Boolean = true,
    crossinline apiCall: suspend () -> Response<T>
): ApiResult<T> {
    val response: Response<T>
    try {
        response = apiCall()
    } catch (t: Throwable) {
        if (logCall) {
            Log.e("API call failed", t.message ?: "Unknown error")
        }
        return mapNetworkError(t)
    }

    val errorBodyString = response.errorBody()?.string()

    if (logCall) {
        Log.i("API Response",
            "=== RESPONSE START ===\n" +
                    "URL: ${response.raw().request.url}\n" +
                    "Message: ${response.message()}\n" +
                    "Code: ${response.code()}\n" +
                    "Body: ${response.body()}\n" +
                    "Error Body: ${errorBodyString}\n" +
                    "Headers:\n\n${response.headers()}\n" +
                    "Raw: ${response.raw()}\n" +
                    "=== RESPONSE END ==="
        )
    }

    return when {
        !response.isSuccessful -> mapApiError(response.code(), errorBodyString)
        response.code() == HttpURLConnection.HTTP_NO_CONTENT && T::class.java == Unit::class.java ->
            mapSuccess(Unit as T, response.headers())

        response.code() == HttpURLConnection.HTTP_NO_CONTENT -> ApiResult.Error.NoContent
        response.body() == null -> mapNullBodyError()
        else -> mapSuccess(response.body()!!, response.headers())
    }
}

fun mapNetworkError(throwable: Throwable): ApiResult.Error {
    return ApiResult.Error.NetworkError(NetworkError(throwable.message, throwable.cause))
}

fun mapApiError(errorCode: Int, errorBody: String?): ApiResult.Error {
    return ApiResult.Error.NetworkError(ApiError(errorCode, errorBody ?: ""))
}

fun mapNullBodyError(): ApiResult.Error {
    return ApiResult.Error.NetworkError(NullBodyError())
}

fun <T : Any> mapSuccess(payload: T, headers: Headers): ApiResult.Success<T> {
    return ApiResult.Success(payload, headers.toMap())
}