package uk.co.joeshuff.immichframe

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp
import uk.co.joeshuff.immichframe.prefs.ImmichFrameConfigController
import uk.co.joeshuff.immichframe.prefs.PreferenceDataStoreHelper
import javax.inject.Inject

val Context.configPreferences by preferencesDataStore("immichFrameStore")

@HiltAndroidApp
class FrameApplication: Application() {

    @Inject
    lateinit var configController: ImmichFrameConfigController

    override fun onCreate() {
        super.onCreate()

        configController.initDataStore(PreferenceDataStoreHelper(configPreferences))
    }

}