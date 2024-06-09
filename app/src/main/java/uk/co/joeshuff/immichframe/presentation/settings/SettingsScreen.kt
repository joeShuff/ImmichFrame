package uk.co.joeshuff.immichframe.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
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

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    launchHomeSettings: () -> Unit
) {
    val isAppHomeApp by viewModel.appIsHome.collectAsState()

    val immichUrl by viewModel.immichUrl.collectAsState()
    val urlValid by viewModel.immichUrlValidity.collectAsState()

    val immichToken by viewModel.immichToken.collectAsState()

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
            value = immichUrl,
            onValueChange = viewModel::setUrlValue,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Done
            ),
            isError = !urlValid
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Immich Token") },
            value = immichToken,
            onValueChange = viewModel::setToken,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            isError = immichToken.isEmpty()
        )

        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::verifyServer) {
            Text(color = Color.White, text = "Verify Server Connection")
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