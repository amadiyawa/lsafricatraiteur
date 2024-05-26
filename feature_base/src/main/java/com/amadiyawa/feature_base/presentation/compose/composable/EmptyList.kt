package com.amadiyawa.feature_base.presentation.compose.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun EmptyList(
    illustration: Int,
    title: Int,
    body: Int
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = illustration), contentDescription = null)
        TextTitleMedium(
            text = stringResource(id = title),
            Modifier.padding(top = 12.dp),
        )
        TextTitleSmall(
            text = stringResource(id = body),
            Modifier.padding(top = 12.dp),
        )
    }
}
