package uk.co.joeshuff.immichframe.data.api.repository

import uk.co.joeshuff.immichframe.data.api.ImmichUnauthorizedAPI
import uk.co.joeshuff.immichframe.domain.repository.ImmichVerificationRepository
import uk.co.joeshuff.immichframe.domain.model.ValidateAccessTokenResponse
import uk.co.joeshuff.immichframe.util.ApiResult
import uk.co.joeshuff.immichframe.util.ImmichResult
import uk.co.joeshuff.immichframe.util.makeApiRequest
import javax.inject.Inject

class ImmichVerificationRepositoryImpl @Inject constructor(
    private val api: ImmichUnauthorizedAPI
): ImmichVerificationRepository {

    override suspend fun verifyAccessToken(token: String): ImmichResult<ValidateAccessTokenResponse> {
        val response = makeApiRequest { api.validateAccessToken(token) }

        return when (response) {
            is ApiResult.Success -> ImmichResult.success(response.payload.toDomain())
            is ApiResult.Error -> ImmichResult.error(response.exception.message)
        }
    }

}