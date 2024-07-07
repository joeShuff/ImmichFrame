package uk.co.joeshuff.immichframe.data.api

import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import uk.co.joeshuff.immichframe.data.api.models.ValidateAccessTokenResponseEntity

interface ImmichUnauthorizedAPI {

    @POST("/api/auth/validateToken")
    suspend fun validateAccessToken(@Header("x-api-key") token: String): Response<ValidateAccessTokenResponseEntity>
}