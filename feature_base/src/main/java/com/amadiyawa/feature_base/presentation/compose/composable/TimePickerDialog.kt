package com.amadiyawa.feature_base.presentation.compose.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.amadiyawa.feature_base.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirmButton: (Long?) -> Unit,
) {
    val timePickerState = rememberTimePickerState()

    DateDialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .defaultMinSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        MaterialTheme.colorScheme.surface,
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.height(66.dp).fillMaxWidth().background(color = MaterialTheme.colorScheme.background),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                onConfirmButton(
                                    Calendar.getInstance().apply {
                                        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                        set(Calendar.MINUTE, timePickerState.minute)
                                    }.timeInMillis
                                )
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(66.dp).padding(start = 13.dp),
                                imageVector = Icons.Rounded.CheckCircle,
                                contentDescription = "Dismiss dialog",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(Modifier.weight(1f))

                        Text(
                            modifier = Modifier.padding(13.dp),
                            text = stringResource(id = R.string.select_date),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(Modifier.weight(1f))

                        IconButton(
                            onClick = { onDismissRequest() }
                        ) {
                            Icon(
                                modifier = Modifier.size(66.dp).padding(end = 13.dp),
                                imageVector = Icons.Rounded.Cancel,
                                contentDescription = "Dismiss dialog",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TimePicker(
                                state = timePickerState,
                            )
                        }
                    }
                }
            }
        }
    }
}