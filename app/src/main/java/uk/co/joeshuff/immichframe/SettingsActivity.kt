package uk.co.joeshuff.immichframe

import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import uk.co.joeshuff.immichframe.presentation.settings.SettingsScreen
import uk.co.joeshuff.immichframe.viewmodels.SettingsViewModel


@AndroidEntryPoint
class SettingsActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                SettingsScreen(settingsViewModel, launchHomeSettings = { launchHomeSettings() })

                LaunchedEffect(Unit) {
                    settingsViewModel.setAppIsHomeApp(isMyLauncherDefault())
                }
            }
        }
    }

    private fun launchHomeSettings() {
        val callHomeSettingIntent = Intent(Settings.ACTION_HOME_SETTINGS)
        startActivity(callHomeSettingIntent)
    }

    private fun isMyLauncherDefault(): Boolean {
        val filter = IntentFilter(Intent.ACTION_MAIN)
        filter.addCategory(Intent.CATEGORY_HOME)
        val filters: MutableList<IntentFilter> = ArrayList()
        filters.add(filter)

        val myPackageName = packageName
        val activities: List<ComponentName> = ArrayList()
        val packageManager = packageManager as PackageManager

        // You can use name of your package here as third argument
        packageManager.getPreferredActivities(filters, activities, null)
        for (activity in activities) {
            if (myPackageName == activity.packageName) {
                return true
            }
        }
        return false
    }
}