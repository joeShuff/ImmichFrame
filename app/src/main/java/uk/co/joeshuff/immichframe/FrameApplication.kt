package uk.co.joeshuff.immichframe

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

val Context.configPreferences by preferencesDataStore("")


@HiltAndroidApp
class FrameApplication: Application() {

}