package uk.co.joeshuff.immichframe.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url
import uk.co.joeshuff.immichframe.data.api.models.ValidateUserResponseEntity

interface ImmichUnauthorizedAPI {

    companion object {
        const val VALIDATE_TOKEN_API = "/api/users/me"
    }

    @GET
    suspend fun validateUser(
        @Url url: String,
        @Header("x-api-key") token: String
    ): Response<ValidateUserResponseEntity>
}