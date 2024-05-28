package com.amadiyawa.feature_base.presentation.compose.composable

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DateDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    dialogWidth: Dp = 360.dp,
    dialogHeight: Dp = 568.dp,
    dialogColor: Color = Color.Transparent,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties.let {
            DialogProperties(
                dismissOnBackPress = it.dismissOnBackPress,
                dismissOnClickOutside = it.dismissOnClickOutside,
                securePolicy = it.securePolicy,
                usePlatformDefaultWidth = false
            )
        },
        content = {
            Surface(
                color = dialogColor,
                modifier = Modifier.requiredWidth(dialogWidth).heightIn(max = dialogHeight),
                content = content
            )
        }
    )
}