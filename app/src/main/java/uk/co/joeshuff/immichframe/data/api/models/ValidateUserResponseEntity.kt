package uk.co.joeshuff.immichframe.data.api.models

import com.google.gson.annotations.SerializedName
import uk.co.joeshuff.immichframe.domain.model.ValidateUserResponse

data class ValidateUserResponseEntity(
    @SerializedName("name") val name: String
) {
    fun toDomain(): ValidateUserResponse {
        return ValidateUserResponse(name)
    }
}