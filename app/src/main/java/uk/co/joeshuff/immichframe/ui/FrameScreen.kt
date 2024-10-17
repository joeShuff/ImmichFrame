package uk.co.joeshuff.immichframe.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import uk.co.joeshuff.immichframe.R
import uk.co.joeshuff.immichframe.viewmodels.FrameActivityViewModel
import uk.co.joeshuff.immichframe.viewmodels.FrameActivityViewModel.State.OnboardingRequired.NoServer

@Composable
fun FrameScreen(
    viewModel: FrameActivityViewModel,
    onSettingsClicked: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.calculateState()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val state by viewModel.state.collectAsState()

        when (state) {
            NoServer -> NoServerOnboarding(onSettingsClicked)
            else -> FrameScreen(onSettingsClicked)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoServerOnboarding() {
    NoServerOnboarding {

    }
}

@Composable
fun NoServerOnboarding(onSettingsClicked: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.75f)) {
            Text(
                text = stringResource(id = R.string.onboarding_noserver_title),
                style = ImmichFrameTextStyling.Heading
            )
            Text(
                text = stringResource(id = R.string.onboarding_noserver_subtitle),
                style = ImmichFrameTextStyling.Body
            )
            Button(onClick = onSettingsClicked) {
                Text(
                    text = stringResource(id = R.string.onboarding_launchsettings),
                    style = TextStyle(color = Color.White)
                )
            }
        }
    }

}

@Composable
fun FrameScreen(launchSettings: () -> Unit) {
    //TODO: Display pictures
    Text(modifier = Modifier.clickable { launchSettings() }, text = "This is the frame screen")
}