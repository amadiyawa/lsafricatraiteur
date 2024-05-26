package com.amadiyawa.feature_delivery.presentation.screen.deliverycreate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amadiyawa.feature_base.common.res.Dimen
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleLarge
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleSmall
import com.amadiyawa.feature_delivery.R
import com.amadiyawa.feature_delivery.presentation.compose.composable.Toolbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun DeliveryCreateScreen(
    onBackClick: () -> Unit
) {
    val viewModel: DeliveryCreateViewModel = koinViewModel()

    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = { SetUpToolbar(onBackClick = onBackClick) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        SetupContent(
            paddingValues = paddingValues,
            viewModel = viewModel
        )
    }
}

@Composable
private fun SetUpToolbar(
    onBackClick: () -> Unit
) {
    Toolbar(
        centered = true,
        title = stringResource(id = R.string.delivery_create),
        onBackClick = onBackClick
    )
}

@Composable
private fun SetupContent(
    paddingValues: PaddingValues,
    viewModel: DeliveryCreateViewModel
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
private fun HandleUiState(viewModel: DeliveryCreateViewModel) {
    val context = LocalContext.current
    val fullName = viewModel.fullName.collectAsStateWithLifecycle()
    val address = viewModel.address.collectAsStateWithLifecycle()
    val paymentMean = viewModel.paymentMean.collectAsStateWithLifecycle()
    val phoneNumber = viewModel.phoneNumber.collectAsStateWithLifecycle()

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
                    text = stringResource(id = R.string.delivery_create_body)
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
                    value = address.value,
                    onValueChange = { viewModel.onAddressChanged(it) },
                    label = { Text(stringResource(id = R.string.address)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = Dimen.Size.extraLarge),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                )
            }

            item {
                var expanded by remember { mutableStateOf(false) }
                val options = listOf(R.string.om, R.string.momo)

                Row {
                    Badge(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimen.Size.extraLarge)
                            .clickable {
                                expanded = true
                            },
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        content = {
                            TextTitleSmall(
                                modifier = Modifier.padding(10.dp),
                                text = stringResource(id = paymentMean.value),
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = stringResource(id = paymentMean.value),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                    DropdownMenu(
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.surface
                        )
                            .fillMaxWidth(),
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    TextTitleSmall(
                                        modifier = Modifier.padding(7.dp),
                                        text = stringResource(id = option),
                                    )
                                },
                                onClick = {
                                    expanded = false
                                    viewModel.onPaymentMeanChanged(
                                        value = option
                                    )
                                }
                            )
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = phoneNumber.value,
                    onValueChange = { viewModel.onPhoneNumberChanged(it) },
                    label = { Text(stringResource(id = R.string.phone_number)) },
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
                    enabled = fullName.value.isNotBlank() && fullName.value.length > 2 && address.value.isNotBlank() && address.value.length > 2 && phoneNumber.value.isNotBlank() && phoneNumber.value.length > 3,
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
                            contentDescription = stringResource(id = R.string.order),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(Dimen.Size.extraSmall))
                        Text(stringResource(id = R.string.order))
                    }
                }
            }
        }
    }
}