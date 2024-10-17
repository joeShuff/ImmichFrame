package uk.co.joeshuff.immichframe.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.joeshuff.immichframe.domain.usecases.ValidateUserUserCase
import uk.co.joeshuff.immichframe.prefs.usecases.GetLoggedInUserUseCase
import uk.co.joeshuff.immichframe.prefs.usecases.GetServerAPITokenUseCase
import uk.co.joeshuff.immichframe.prefs.usecases.GetServerUrlUseCase
import uk.co.joeshuff.immichframe.prefs.usecases.SetIsLoggedInUseCase
import uk.co.joeshuff.immichframe.prefs.usecases.SetLoggedInUserUseCase
import uk.co.joeshuff.immichframe.prefs.usecases.SetServerDetailsUseCase
import uk.co.joeshuff.immichframe.util.Status
import uk.co.joeshuff.immichframe.util.toBaseUrl
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val validateUserUserCase: ValidateUserUserCase,
    private val getServerUrlUseCase: GetServerUrlUseCase,
    private val getServerAPITokenUseCase: GetServerAPITokenUseCase,
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase,
    private val setServerDetailsUseCase: SetServerDetailsUseCase,
    private val setLoggedInUserUseCase: SetLoggedInUserUseCase,
    private val setLoggedInUseCase: SetIsLoggedInUseCase
) : ViewModel() {

    sealed class VerifyServerState {
        data object Presenting : VerifyServerState()
        data object Success : VerifyServerState()
        data object Loading : VerifyServerState()
        data class Error(val errorMessage: String) : VerifyServerState()
    }

    private val _verifyState: MutableStateFlow<VerifyServerState> =
        MutableStateFlow(VerifyServerState.Presenting)
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

    //region Validated user
    private val _loggedInUser: MutableStateFlow<String?> = MutableStateFlow(null)
    val loggedInUser: StateFlow<String?> = _loggedInUser.asStateFlow()
    //endregion

    fun loadConfig() = viewModelScope.launch {
        _immichUrl.update { getServerUrlUseCase() ?: "" }
        _immichToken.update { getServerAPITokenUseCase() ?: "" }
        _loggedInUser.update { getLoggedInUserUseCase() }
    }

    fun verifyServer() = viewModelScope.launch {
        _verifyState.update { VerifyServerState.Loading }
        _urlFieldEnabled.update { false }
        _tokenFieldEnabled.update { false }

        val url = _immichUrl.value.toBaseUrl()
        val verificationResponse = validateUserUserCase(url, _immichToken.value)

        when (verificationResponse.status) {
            Status.SUCCESS -> {
                _verifyState.update { VerifyServerState.Success }
                verificationResponse.data?.name?.let { showVerifiedUser(it) }
                storeFieldsInPrefs()
            }

            Status.ERROR -> {
                _verifyState.update { VerifyServerState.Error(verificationResponse.message ?: "") }
            }
        }

        _urlFieldEnabled.update { true }
        _tokenFieldEnabled.update { true }
    }

    private suspend fun showVerifiedUser(name: String) {
        _loggedInUser.update { name }
        setLoggedInUserUseCase(name)
        setLoggedInUseCase(true)
    }

    private suspend fun storeFieldsInPrefs() {
        setServerDetailsUseCase(_immichUrl.value, _immichToken.value)
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