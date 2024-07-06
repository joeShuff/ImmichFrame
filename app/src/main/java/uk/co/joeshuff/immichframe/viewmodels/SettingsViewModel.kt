package uk.co.joeshuff.immichframe.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.joeshuff.immichframe.prefs.ImmichFrameConfigController
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val configController: ImmichFrameConfigController
): ViewModel() {

    //region Home App Settings
    private val _appIsHomeApp: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val appIsHome = _appIsHomeApp.asStateFlow()
    //endregion

    //region Server config
    private val _immichUrl: MutableStateFlow<String> = MutableStateFlow("")
    val immichUrl = _immichUrl.asStateFlow()

    private val _immichUrlValidity: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val immichUrlValidity = _immichUrlValidity.asStateFlow()

    private val _immichToken: MutableStateFlow<String> = MutableStateFlow("")
    val immichToken = _immichToken.asStateFlow()
    //endregion

    //region Sources config
    private val _configureSourcesSectionVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val configureSourcesSectionVisible = _configureSourcesSectionVisible.asStateFlow()

    //Selected albums
    private val _chosenAlbums: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val chosenAlbums = _chosenAlbums.asStateFlow()

    //endregion

    fun loadConfig() = viewModelScope.launch {
        _immichUrl.update { configController.getKeyValue(ImmichFrameConfigController.IMMICH_URL_KEY) }
         _immichToken.update { configController.getKeyValue(ImmichFrameConfigController.IMMICH_API_TOKEN_KEY) }
    }

    fun verifyServer() {
        storeFieldsInPrefs()
    }

    private fun storeFieldsInPrefs() = viewModelScope.launch {
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