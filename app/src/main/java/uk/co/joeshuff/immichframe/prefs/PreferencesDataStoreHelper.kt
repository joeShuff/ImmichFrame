package uk.co.joeshuff.immichframe.prefs

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferenceDataStoreHelper(private val dataSource: DataStore<Preferences>) :
    IPreferenceDataStoreAPI {

    companion object {
        val tag = PreferenceDataStoreHelper::class.java.simpleName
    }

    /* This returns us a flow of data from DataStore.
    Basically as soon we update the value in Datastore,
    the values returned by it also changes. */
    override fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        dataSource.data
            .catch { exception ->
                Log.e(tag, "Failed to load pref $key", exception)
                emit(emptyPreferences())
            }
            .map { preferences ->
                val result = preferences[key] ?: defaultValue
                result
            }

    /* This returns the last saved value of the key. If we change the value,
    it wont effect the values produced by this function */
    override suspend fun <T> getFirstPreference(key: Preferences.Key<T>, defaultValue: T): T =
        dataSource.data.first()[key] ?: defaultValue

    // This Sets the value based on the value passed in value parameter.
    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        dataSource.edit { preferences -> preferences[key] = value }
    }

    // This Function removes the Key Value pair from the datastore, hereby removing it completely.
    override suspend fun <T> removePreference(key: Preferences.Key<T>) {
        dataSource.edit { preferences -> preferences.remove(key) }
    }

    // This function clears the entire Preference Datastore.
    override suspend fun clearAllPreference() {
        dataSource.edit { preferences -> preferences.clear() }
    }

    override suspend fun getAllPreferences(): Preferences = dataSource.data.first()

    override fun getAllPreferencesFlow(): Flow<Preferences> = dataSource.data
}
