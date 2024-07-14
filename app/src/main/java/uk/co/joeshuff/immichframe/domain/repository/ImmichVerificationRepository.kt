package uk.co.joeshuff.immichframe.domain.repository

import uk.co.joeshuff.immichframe.util.ImmichResult
import uk.co.joeshuff.immichframe.domain.model.ValidateUserResponse

interface ImmichVerificationRepository {

    suspend fun verifyAccessToken(url: String, token: String): ImmichResult<ValidateUserResponse>

}