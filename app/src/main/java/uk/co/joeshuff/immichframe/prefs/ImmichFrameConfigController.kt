package uk.co.joeshuff.immichframe.prefs

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.co.joeshuff.immichframe.util.toBaseUrl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImmichFrameConfigController @Inject constructor() {

    private lateinit var dataStoreHelper: PreferenceDataStoreHelper

    var cachedServerAddress: String = ""
        private set

    fun initDataStore(dataStore: PreferenceDataStoreHelper) {
        dataStoreHelper = dataStore
    }

    fun setCachedAddress(address: String) {
        cachedServerAddress = address.toBaseUrl()
    }

    suspend fun setKeyValue(key: String, value: String) {
        dataStoreHelper.putPreference(stringPreferencesKey(key), value)
    }

    suspend fun getKeyValue(key: String, defaultValue: String = ""): String {
        return dataStoreHelper.getFirstPreference(stringPreferencesKey(key), defaultValue)
            ?: defaultValue
    }

    companion object {
        const val IMMICH_URL_KEY = "immich_base_url"
        const val IMMICH_API_TOKEN_KEY = "immich_api_token_key"
    }
}