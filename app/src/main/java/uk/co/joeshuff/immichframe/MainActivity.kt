package uk.co.joeshuff.immichframe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uk.co.joeshuff.immichframe.prefs.ImmichFrameConfigController
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                FrameScreen {
                    launchSettings()
                }
            }
        }
    }

    private fun launchSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}


@Composable
fun FrameScreen(onSettingsClicked: () -> Unit) {
    Button(modifier = Modifier.fillMaxWidth().height(60.dp), onClick = onSettingsClicked) {
        androidx.compose.material.Text(color = Color.White, text = "Launch Settings")
    }
}