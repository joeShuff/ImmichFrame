package uk.co.joeshuff.immichframe.data.api.models

import com.google.gson.annotations.SerializedName
import uk.co.joeshuff.immichframe.domain.model.ValidateAccessTokenResponse

data class ValidateAccessTokenResponseEntity(
    @SerializedName("authStatus") val authStatus: Boolean
) {
    fun toDomain(): ValidateAccessTokenResponse {
        return ValidateAccessTokenResponse(authStatus)
    }
}