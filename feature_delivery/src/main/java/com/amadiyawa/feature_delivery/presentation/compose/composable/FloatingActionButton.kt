package com.amadiyawa.feature_delivery.presentation.compose.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.amadiyawa.feature_base.common.res.Dimen
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleMedium

@Composable
fun FloatingActionButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    isVisible: Boolean = true,
    label: String
) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically { with(density) { 40.dp.roundToPx() } } + fadeIn(),
        exit = fadeOut(animationSpec = keyframes { this.durationMillis = 120 })
    ) {
        ExtendedFloatingActionButton(
            onClick = onClick,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimen.Spacing.medium)
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                TextTitleMedium(text = label)
            }
        }
    }
}