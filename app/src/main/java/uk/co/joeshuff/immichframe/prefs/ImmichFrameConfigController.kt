package uk.co.joeshuff.immichframe.prefs

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImmichFrameConfigController @Inject constructor() {

    private lateinit var dataStoreHelper: PreferenceDataStoreHelper

    fun initDataStore(dataStore: PreferenceDataStoreHelper) {
        dataStoreHelper = dataStore
    }

    suspend fun setKeyValue(key: String, value: String) {
        dataStoreHelper.putPreference(stringPreferencesKey(key), value)
    }

    fun getKeyValue(key: String, defaultValue: String? = null): String? = runBlocking {
        withTimeoutOrNull(200) {
            return@withTimeoutOrNull getKeyValueAsync(key, defaultValue)
        }
    }

    suspend fun getKeyValueAsync(key: String, defaultValue: String? = null): String? {
        return dataStoreHelper.getFirstPreference(stringPreferencesKey(key), defaultValue)
    }

    companion object {
        const val IMMICH_URL_KEY = "immich_base_url"
        const val IMMICH_API_TOKEN_KEY = "immich_api_token_key"
        const val IMMICH_LOGGED_IN_USER_NAME = "immich_logged_in_user_name"
    }
}