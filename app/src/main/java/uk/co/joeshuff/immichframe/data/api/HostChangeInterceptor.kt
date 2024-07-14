package uk.co.joeshuff.immichframe.data.api

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import uk.co.joeshuff.immichframe.prefs.ImmichFrameConfigController
import uk.co.joeshuff.immichframe.util.findPort
import uk.co.joeshuff.immichframe.util.findResource
import uk.co.joeshuff.immichframe.util.findScheme
import java.lang.IllegalArgumentException
import javax.inject.Inject

class HostChangeInterceptor @Inject constructor(
    private val config: ImmichFrameConfigController
): Interceptor {

    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val url = config.getKeyValue(ImmichFrameConfigController.IMMICH_URL_KEY)
        url?.let { host ->
            try {
                val newUrlBuilder = request.url.newBuilder()
                    .scheme(host.findScheme()?.replace("://", "") ?: "https")
                    .host(host.findResource())

                host.findPort().toIntOrNull()?.let {
                    newUrlBuilder.port(it)
                }

                val newUrl = newUrlBuilder.build()

                request = request.newBuilder()
                    .url(newUrl)
                    .build()

                return chain.proceed(request)
            } catch (e: IllegalArgumentException) {
                return Response.Builder()
                    .request(request)
                    .code(999)
                    .protocol(Protocol.HTTP_1_1)
                    .message("Error parsing host: $e")
                    .body("{\"error\": \"Error parsing ${host}\"}".toResponseBody())
                    .build()
            }
        }

        return chain.proceed(request)
    }

}