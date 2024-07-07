package uk.co.joeshuff.immichframe.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.joeshuff.immichframe.domain.usecases.VerifyTokenUseCase
import uk.co.joeshuff.immichframe.prefs.ImmichFrameConfigController
import uk.co.joeshuff.immichframe.util.ApiResult
import uk.co.joeshuff.immichframe.util.Status
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val configController: ImmichFrameConfigController,
    private val verifyTokenUseCase: VerifyTokenUseCase
): ViewModel() {

    sealed class VerifyServerState {
        data object Presenting: VerifyServerState()
        data object Success: VerifyServerState()
        data object Loading: VerifyServerState()
        data class Error(val errorMessage: String): VerifyServerState()
    }

    private val _verifyState: MutableStateFlow<VerifyServerState> = MutableStateFlow(VerifyServerState.Presenting)
    val verifyState = _verifyState.asStateFlow()

    //region Home App Settings
    private val _appIsHomeApp: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val appIsHome = _appIsHomeApp.asStateFlow()
    //endregion

    //region Server URL
    private val _immichUrl: MutableStateFlow<String> = MutableStateFlow("")
    val immichUrl = _immichUrl.asStateFlow()

    private val _urlFieldEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val urlFieldEnabled = _urlFieldEnabled.asStateFlow()

    private val _immichUrlValidity: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val immichUrlValidity = _immichUrlValidity.asStateFlow()
    //endregion

    //region tokenField
    private val _immichToken: MutableStateFlow<String> = MutableStateFlow("")
    val immichToken = _immichToken.asStateFlow()

    private val _tokenFieldEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val tokenFieldEnabled = _tokenFieldEnabled.asStateFlow()
    //endregion

    fun loadConfig() = viewModelScope.launch {
        _immichUrl.update { configController.getKeyValue(ImmichFrameConfigController.IMMICH_URL_KEY) }
         _immichToken.update { configController.getKeyValue(ImmichFrameConfigController.IMMICH_API_TOKEN_KEY) }
    }

    fun verifyServer() = viewModelScope.launch {
        configController.setCachedAddress(_immichUrl.value)

        _verifyState.update { VerifyServerState.Loading }
        _urlFieldEnabled.update { false }
        _tokenFieldEnabled.update { false }

        val verificationResponse = verifyTokenUseCase(_immichToken.value)

        when (verificationResponse.status) {
            Status.SUCCESS -> {
                _verifyState.update { VerifyServerState.Success }
                storeFieldsInPrefs()
            }
            Status.ERROR -> {
                _verifyState.update { VerifyServerState.Error(verificationResponse.message?: "") }
            }
        }

        _urlFieldEnabled.update { true }
        _tokenFieldEnabled.update { true }
    }

    private fun storeFieldsInPrefs() = viewModelScope.launch {
        configController.setCachedAddress(_immichUrl.value)
        configController.setKeyValue(ImmichFrameConfigController.IMMICH_URL_KEY, _immichUrl.value)
        configController.setKeyValue(ImmichFrameConfigController.IMMICH_API_TOKEN_KEY, _immichToken.value)
    }

    fun setUrlValue(url: String) {
        _immichUrl.update { url }
        //todo: validity
    }

    fun setToken(token: String) {
        _immichToken.update { token }
    }

    fun setAppIsHomeApp(isHomeApp: Boolean) {
        _appIsHomeApp.update { isHomeApp }
    }
}