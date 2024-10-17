package uk.co.joeshuff.immichframe.prefs.usecases

import uk.co.joeshuff.immichframe.prefs.ImmichFrameConfigController
import uk.co.joeshuff.immichframe.prefs.ImmichFrameConfigController.Companion.IMMICH_API_TOKEN_KEY
import uk.co.joeshuff.immichframe.prefs.ImmichFrameConfigController.Companion.IMMICH_LOGGED_IN
import uk.co.joeshuff.immichframe.prefs.ImmichFrameConfigController.Companion.IMMICH_LOGGED_IN_USER_NAME
import uk.co.joeshuff.immichframe.prefs.ImmichFrameConfigController.Companion.IMMICH_URL_KEY
import javax.inject.Inject

//region ServerDetails
class SetServerDetailsUseCase @Inject constructor(
    private val controller: ImmichFrameConfigController
) {
    suspend operator fun invoke(serverUrl: String, apiToken: String) {
        controller.setKeyValue(IMMICH_URL_KEY, serverUrl)
        controller.setKeyValue(IMMICH_API_TOKEN_KEY, apiToken)
    }
}

class GetServerUrlUseCase @Inject constructor(
    private val controller: ImmichFrameConfigController
) {
    suspend operator fun invoke() =
        controller.getKeyValueAsync(IMMICH_URL_KEY)
}

class GetServerAPITokenUseCase @Inject constructor(
    private val controller: ImmichFrameConfigController
) {
    suspend operator fun invoke() =
        controller.getKeyValueAsync(IMMICH_API_TOKEN_KEY)
}
//endregion


//region Logged In records
class SetLoggedInUserUseCase @Inject constructor(
    private val controller: ImmichFrameConfigController
) {
    suspend operator fun invoke(username: String) {
        controller.setKeyValue(IMMICH_LOGGED_IN_USER_NAME, username)
    }
}

class GetLoggedInUserUseCase @Inject constructor(
    private val controller: ImmichFrameConfigController
) {
    suspend operator fun invoke() =
        controller.getKeyValueAsync(IMMICH_LOGGED_IN_USER_NAME)
}

class GetIsLoggedInUseCase @Inject constructor(
    private val controller: ImmichFrameConfigController
) {
    suspend operator fun invoke() =
        controller.getKeyValueAsync(IMMICH_LOGGED_IN, false)
}

class SetIsLoggedInUseCase @Inject constructor(
    private val controller: ImmichFrameConfigController
) {
    suspend operator fun invoke(isLoggedIn: Boolean) =
        controller.setKeyValue(IMMICH_LOGGED_IN, isLoggedIn)
}
//endregion