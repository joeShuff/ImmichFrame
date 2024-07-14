package uk.co.joeshuff.immichframe.domain.repository

import uk.co.joeshuff.immichframe.util.ImmichResult
import uk.co.joeshuff.immichframe.domain.model.ValidateUserResponse

interface ImmichVerificationRepository {

    suspend fun validateUser(url: String, token: String): ImmichResult<ValidateUserResponse>

}