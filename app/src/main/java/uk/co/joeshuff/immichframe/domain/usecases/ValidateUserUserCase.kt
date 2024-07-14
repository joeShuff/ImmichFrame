package uk.co.joeshuff.immichframe.domain.usecases

import uk.co.joeshuff.immichframe.domain.repository.ImmichVerificationRepository
import javax.inject.Inject

class ValidateUserUserCase @Inject constructor(private val repository: ImmichVerificationRepository) {
    suspend operator fun invoke(url: String, token: String) = repository.validateUser(url, token)
}