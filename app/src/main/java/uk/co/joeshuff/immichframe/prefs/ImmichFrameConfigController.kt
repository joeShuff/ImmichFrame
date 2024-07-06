package uk.co.joeshuff.immichframe.prefs

import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImmichFrameConfigController @Inject constructor() {

    private var dataStoreHelper: PreferenceDataStoreHelper? = null

    fun initDataStore(dataStore: PreferenceDataStoreHelper) {
        dataStoreHelper = dataStore
    }

    suspend fun setKeyValue(key: String, value: String) {
        dataStoreHelper?.putPreference(stringPreferencesKey(key), value)
    }

    suspend fun getKeyValue(key: String, defaultValue: String = ""): String {
        return dataStoreHelper?.getFirstPreference(stringPreferencesKey(key), defaultValue)?: defaultValue
    }

    companion object {
        const val IMMICH_URL_KEY = "immich_base_url"
        const val IMMICH_API_TOKEN_KEY = "immich_api_token_key"
    }
}