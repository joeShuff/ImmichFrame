package uk.co.joeshuff.immichframe.presentation.settings

import android.widget.Spinner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.co.joeshuff.immichframe.R
import uk.co.joeshuff.immichframe.presentation.components.Separator
import uk.co.joeshuff.immichframe.viewmodels.SettingsViewModel
import uk.co.joeshuff.immichframe.viewmodels.SettingsViewModel.VerifyServerState

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    launchHomeSettings: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadConfig()
    }

    val isAppHomeApp by viewModel.appIsHome.collectAsState()

    val verifyState by viewModel.verifyState.collectAsState()

    val immichUrl by viewModel.immichUrl.collectAsState()
    val urlEnabled by viewModel.urlFieldEnabled.collectAsState()
    val urlValid by viewModel.immichUrlValidity.collectAsState()

    val loggedInUser by viewModel.loggedInUser.collectAsState()
    
    val immichToken by viewModel.immichToken.collectAsState()
    val tokenEnabled by viewModel.tokenFieldEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HomeAppSection(isAppHomeApp = isAppHomeApp, setHomeAppOnClick = launchHomeSettings)

        Separator()

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Immich URL") },
            enabled = urlEnabled,
            value = immichUrl,
            singleLine = true,
            onValueChange = viewModel::setUrlValue,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Next
            ),
            isError = !urlValid
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Immich Token") },
            enabled = tokenEnabled,
            value = immichToken,
            singleLine = true,
            onValueChange = viewModel::setToken,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            isError = immichToken.isEmpty()
        )

        val buttonColor = when (verifyState) {
            is VerifyServerState.Success -> Color.Green
            is VerifyServerState.Error -> Color.Red
            else -> Color.Blue
        }

        loggedInUser?.let { 
            Text(text = "Logged in as $it", color = Color.Green)
        }
        
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor
            ),
            enabled = verifyState != VerifyServerState.Loading,
            onClick = viewModel::verifyServer
        ) {
            if (verifyState == VerifyServerState.Loading) {
                CircularProgressIndicator()
            } else {
                Text(color = Color.White, text = "Verify Server Connection")
            }
        }
    }
}

@Composable
private fun HomeAppSection(isAppHomeApp: Boolean, setHomeAppOnClick: () -> Unit) {
    var colors = ButtonDefaults.buttonColors()

    when (isAppHomeApp) {
        true -> colors =
            ButtonDefaults.buttonColors(containerColor = Color.Green, contentColor = Color.Black)

        false -> colors =
            ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White)
    }

    val buttonText =
        if (isAppHomeApp) R.string.homeapp_already_set else R.string.homeapp_needs_setting

    Button(colors = colors, modifier = Modifier.fillMaxWidth(), onClick = setHomeAppOnClick) {
        Text(text = stringResource(id = buttonText))
    }

    //todo: Checkbox to acknowledge you're not setting as launcher so no warnings
}

@Preview
@Composable
fun PreviewHomeAppSectionEnabled() {
    HomeAppSection(isAppHomeApp = true, setHomeAppOnClick = {})
}

@Preview
@Composable
fun PreviewHomeAppSectionDisabled() {
    HomeAppSection(isAppHomeApp = false, setHomeAppOnClick = {})
}


@Preview(showBackground = true, heightDp = 1200, widthDp = 800)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen(hiltViewModel(), {})
}