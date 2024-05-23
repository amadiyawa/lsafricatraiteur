package com.amadiyawa.feature_contact.presentation.screen.contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amadiyawa.feature_base.common.res.Dimen
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleLarge
import com.amadiyawa.feature_contact.R
import com.amadiyawa.feature_contact.presentation.compose.composable.Toolbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactScreen(
    onBackClick: () -> Unit
) {
    val viewModel: ContactViewModel = koinViewModel()

    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = { SetUpToolbar() },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        SetupContent(
            paddingValues = paddingValues,
            viewModel = viewModel
        )
    }
}

@Composable
private fun SetUpToolbar() {
    Toolbar(
        centered = false,
        title = stringResource(id = R.string.contact),
        onBackClick = {  }
    )
}

@Composable
private fun SetupContent(
    paddingValues: PaddingValues,
    viewModel: ContactViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){
        HandleUiState(viewModel = viewModel)
    }
}

@Composable
private fun HandleUiState(viewModel: ContactViewModel) {
    val context = LocalContext.current
    val fullName = viewModel.fullName.collectAsStateWithLifecycle()
    val purpose = viewModel.purpose.collectAsStateWithLifecycle()
    val message = viewModel.message.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(Dimen.Padding.screenContent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimen.Padding.screenContent)
        ) {
            item {
                TextTitleLarge(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.contact_body)
                )
            }

            item {
                OutlinedTextField(
                    value = fullName.value,
                    onValueChange = { viewModel.onFullNameChanged(it) },
                    label = { Text(stringResource(id = R.string.full_name)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = Dimen.Size.extraLarge),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                )
            }

            item {
                OutlinedTextField(
                    value = purpose.value,
                    onValueChange = { viewModel.onPurposeChanged(it) },
                    label = { Text(stringResource(id = R.string.purpose)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = Dimen.Size.extraLarge),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                )
            }

            item {
                OutlinedTextField(
                    value = message.value,
                    onValueChange = { viewModel.onMessageChanged(it) },
                    label = { Text(stringResource(id = R.string.message)) },
                    singleLine = false,
                    maxLines = 5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = Dimen.Size.extraLarge),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                )
            }

            item {
                Button(
                    onClick = { viewModel.sendWhatsAppMessage(
                        context = context
                    ) },
                    enabled = fullName.value.isNotBlank() && fullName.value.length > 2 && purpose.value.isNotBlank() && purpose.value.length > 2 && message.value.isNotBlank() && message.value.length > 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimen.Size.extraLarge)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Whatsapp,
                            contentDescription = stringResource(id = R.string.send),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(Dimen.Size.extraSmall))
                        Text(stringResource(id = R.string.send))
                    }
                }
            }
        }
    }
}