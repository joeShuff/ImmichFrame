package uk.co.joeshuff.immichframe.domain.repository

import uk.co.joeshuff.immichframe.util.ImmichResult
import uk.co.joeshuff.immichframe.domain.model.ValidateAccessTokenResponse

interface ImmichVerificationRepository {

    suspend fun verifyAccessToken(token: String): ImmichResult<ValidateAccessTokenResponse>

}