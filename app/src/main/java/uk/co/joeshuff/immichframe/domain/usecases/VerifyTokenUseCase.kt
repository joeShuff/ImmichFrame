package uk.co.joeshuff.immichframe.domain.usecases

import uk.co.joeshuff.immichframe.domain.repository.ImmichVerificationRepository
import javax.inject.Inject

class VerifyTokenUseCase @Inject constructor(private val repository: ImmichVerificationRepository) {
    suspend operator fun invoke(token: String) = repository.verifyAccessToken(token)
}