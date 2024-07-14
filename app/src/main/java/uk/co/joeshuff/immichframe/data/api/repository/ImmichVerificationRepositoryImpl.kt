package uk.co.joeshuff.immichframe.data.api.repository

import uk.co.joeshuff.immichframe.data.api.ImmichUnauthorizedAPI
import uk.co.joeshuff.immichframe.domain.repository.ImmichVerificationRepository
import uk.co.joeshuff.immichframe.domain.model.ValidateUserResponse
import uk.co.joeshuff.immichframe.util.ApiResult
import uk.co.joeshuff.immichframe.util.ImmichResult
import uk.co.joeshuff.immichframe.util.makeApiRequest
import javax.inject.Inject

class ImmichVerificationRepositoryImpl @Inject constructor(
    private val api: ImmichUnauthorizedAPI
) : ImmichVerificationRepository {

    override suspend fun validateUser(
        url: String,
        token: String
    ): ImmichResult<ValidateUserResponse> {
        val response = makeApiRequest {
            api.validateUser(
                url + ImmichUnauthorizedAPI.VALIDATE_USER_ENDPOINT,
                token
            )
        }

        return when (response) {
            is ApiResult.Success -> ImmichResult.success(response.payload.toDomain())
            is ApiResult.Error -> ImmichResult.error(response.exception.message)
        }
    }

}