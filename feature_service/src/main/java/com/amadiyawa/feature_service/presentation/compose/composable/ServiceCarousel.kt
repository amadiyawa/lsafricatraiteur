package com.amadiyawa.feature_service.presentation.compose.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amadiyawa.feature_base.presentation.compose.composable.DottedPagerIndicator
import com.amadiyawa.feature_base.presentation.compose.composable.ImageCard
import com.amadiyawa.feature_service.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ServiceCarousel(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState { 3 }
    val coroutineScope = rememberCoroutineScope()
    val isUserInteracting = pagerState.interactionSource.collectIsDraggedAsState()

    // Launch a coroutine to change page every 5 seconds
    LaunchedEffect(key1 = pagerState) {
        coroutineScope.launch {
            try {
                while (true) {
                    delay(10000L) // delay for 10 seconds
                    if (!isUserInteracting.value) {
                        val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = modifier.padding(start = 10.dp, end = 10.dp)
        ) { page ->
            when (page) {
                0 -> {
                    ImageCard(imageResId = R.drawable.image_one)
                }
                1 -> {
                    ImageCard(imageResId = R.drawable.image_two)
                }
                else -> {
                    ImageCard(imageResId = R.drawable.image_three)
                }
            }
        }
        DottedPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(6.dp)
        )
    }
}