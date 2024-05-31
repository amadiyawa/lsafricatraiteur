@file:OptIn(ExperimentalMaterial3Api::class)

package com.amadiyawa.feature_delivery.presentation.screen.deliverycreate

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
import com.amadiyawa.feature_base.common.util.TimeUtil.Companion.getCurrentYear
import com.amadiyawa.feature_base.common.util.formatDate
import com.amadiyawa.feature_base.common.util.formatTime
import com.amadiyawa.feature_base.presentation.compose.composable.DatePickerDialog
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleLarge
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleSmall
import com.amadiyawa.feature_base.presentation.compose.composable.TimePickerDialog
import com.amadiyawa.feature_delivery.R
import com.amadiyawa.feature_delivery.domain.model.Menu
import com.amadiyawa.feature_delivery.presentation.compose.composable.Toolbar
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@Composable
fun DeliveryCreateScreen(
    onBackClick: () -> Unit
) {
    val viewModel: DeliveryCreateViewModel = koinViewModel()
    viewModel.onEnter()

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
    val uiState = viewModel.uiStateFlow.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){
        HandleUiState(
            viewModel = viewModel,
            uiState = uiState
        )
    }
}

@Composable
private fun HandleUiState(
    viewModel: DeliveryCreateViewModel,
    uiState: State<DeliveryCreateViewModel.UiState>,
) {
    when (uiState.value) {
        is DeliveryCreateViewModel.UiState.MenuListContent -> {
            DeliveryForm(
                viewModel = viewModel
            )
        }
        is DeliveryCreateViewModel.UiState.EmptyMenuList -> {
            // Handle empty state
        }
        is DeliveryCreateViewModel.UiState.Loading -> {
            // Handle loading state
        }
        DeliveryCreateViewModel.UiState.ErrorMenuList -> {
            // Handle error state
        }
    }
}

@Composable
private fun DeliveryForm(
    viewModel: DeliveryCreateViewModel
) {
    val context = LocalContext.current
    val selectDate = stringResource(id = R.string.date)
    val selectTime = stringResource(id = R.string.time)
    val fullName = viewModel.fullName.collectAsStateWithLifecycle()
    val address = viewModel.address.collectAsStateWithLifecycle()
    val selectedMenuList = viewModel.selectedMenuList.collectAsStateWithLifecycle()
    val paymentMean = viewModel.paymentMean.collectAsStateWithLifecycle()
    val phoneNumber = viewModel.phoneNumber.collectAsStateWithLifecycle()
    val selectedDate = remember { mutableStateOf(selectDate) }
    val selectedTime = remember { mutableStateOf(selectTime) }
    val showCustomDatePickerDialog = remember { mutableStateOf(false) }
    val showCustomTimePickerDialog = remember { mutableStateOf(false) }

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
                MenuSelection(
                    viewModel = viewModel
                )
            }

            item {
                SelectedMenuList(
                    deliveryCreateViewModel = viewModel,
                    selectedMenuList = selectedMenuList.value
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
                        modifier = Modifier
                            .background(
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
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        PickDate(
                            viewModel = viewModel,
                            selectedDate = selectedDate,
                            showCustomDatePickerDialog = showCustomDatePickerDialog
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        PickTime(
                            viewModel = viewModel,
                            selectedTime = selectedTime,
                            showCustomTimePickerDialog = showCustomTimePickerDialog
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        viewModel.sendWhatsAppMessage(context = context)
                    },
                    enabled = viewModel.validateForm(),
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

@Composable
private fun MenuSelection(
    viewModel: DeliveryCreateViewModel
) {
    val filteredMenuList = viewModel.filteredMenuList.collectAsStateWithLifecycle()
    val menu = viewModel.menu.collectAsStateWithLifecycle()

    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = menu.value,
            onValueChange = { viewModel.onMenuChanged(it) },
            label = { Text(stringResource(id = R.string.select_menu)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = Dimen.Size.extraLarge),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
        )

        AnimatedVisibility(visible = filteredMenuList.value.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                filteredMenuList.value.forEach { menu ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expanded = false
                                viewModel.onMenuSelected(
                                    menu = menu
                                )
                            }
                    ) {
                        TextTitleSmall(
                            modifier = Modifier
                                .padding(7.dp)
                                .fillMaxWidth(),
                            text = menu.name,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectedMenuList(
    deliveryCreateViewModel: DeliveryCreateViewModel,
    selectedMenuList: List<Menu>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimen.Spacing.small)
    ) {
        selectedMenuList.forEach { menuItem ->
            Badge(
                modifier = Modifier
                    .clickable {
                        deliveryCreateViewModel.onMenuRemoved(menu = menuItem)
                    },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                content = {
                    TextTitleSmall(
                        modifier = Modifier.padding(10.dp),
                        text = menuItem.name,
                    )
                }
            )
        }
    }
}

@Composable
private fun PickDate(
    viewModel: DeliveryCreateViewModel,
    selectedDate: MutableState<String>,
    showCustomDatePickerDialog: MutableState<Boolean>
) {
    val selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val currentDate = Calendar.getInstance().apply {
                // Reset hour, minutes, seconds and milliseconds
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            // utcTimeMillis should be greater or equal to currentDate to be selectable
            return utcTimeMillis >= currentDate
        }

        // users cannot select the years from 2024
        override fun isSelectableYear(year: Int): Boolean {
            return year <= getCurrentYear()
        }
    }

    Badge(
        modifier = Modifier
            .requiredHeight(Dimen.Size.extraLarge)
            .clickable {
                showCustomDatePickerDialog.value = true
            },
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        content = {
            Row(
                modifier = Modifier.padding(Dimen.Spacing.medium),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextTitleSmall(
                    text = selectedDate.value,
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = selectedDate.value,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
    )
    if (showCustomDatePickerDialog.value) {
        DatePickerDialog(
            selectedDate = System.currentTimeMillis(),
            onDismissRequest = { showCustomDatePickerDialog.value = false },
            onConfirmButton = {
                selectedDate.value = formatDate(it!!)
                viewModel.onDateSelected(it)
                showCustomDatePickerDialog.value = false
            },
                selectableDates = selectableDates
        )
    }
}

@Composable
private fun PickTime(
    viewModel: DeliveryCreateViewModel,
    selectedTime: MutableState<String>,
    showCustomTimePickerDialog: MutableState<Boolean>
) {
    Badge(
        modifier = Modifier
            .requiredHeight(Dimen.Size.extraLarge)
            .clickable {
                showCustomTimePickerDialog.value = true
            },
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        content = {
            Row(
                modifier = Modifier.padding(Dimen.Spacing.medium),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                TextTitleSmall(
                    text = selectedTime.value,
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = selectedTime.value,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
    if (showCustomTimePickerDialog.value) {
        TimePickerDialog(
            onDismissRequest = { showCustomTimePickerDialog.value = false },
            onConfirmButton = {
                selectedTime.value = formatTime(it!!)
                viewModel.onTimeSelected(it)
                showCustomTimePickerDialog.value = false
            }
        )
    }
}