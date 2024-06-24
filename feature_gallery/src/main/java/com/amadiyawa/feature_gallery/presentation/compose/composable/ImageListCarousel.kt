package com.amadiyawa.feature_gallery.presentation.compose.composable

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
import com.amadiyawa.feature_gallery.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageListCarousel(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState { 10 }
    val coroutineScope = rememberCoroutineScope()
    val isUserInteracting = pagerState.interactionSource.collectIsDraggedAsState()

    // Launch a coroutine to change page every 15 seconds
    LaunchedEffect(key1 = pagerState) {
        coroutineScope.launch {
            try {
                while (true) {
                    delay(15000L) // delay for 15 seconds
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
            modifier = modifier.weight(0.95f)
        ) { page ->
            when (page) {
                0 -> {
                    ImageCard(imageResId = R.drawable.image_one)
                }
                1 -> {
                    ImageCard(imageResId = R.drawable.image_two)
                }
                2 -> {
                    ImageCard(imageResId = R.drawable.image_three)
                }
                3 -> {
                    ImageCard(imageResId = R.drawable.image_four)
                }
                4 -> {
                    ImageCard(imageResId = R.drawable.image_five)
                }
                5 -> {
                    ImageCard(imageResId = R.drawable.image_six)
                }
                6 -> {
                    ImageCard(imageResId = R.drawable.image_seven)
                }
                7 -> {
                    ImageCard(imageResId = R.drawable.image_eight)
                }
                8 -> {
                    ImageCard(imageResId = R.drawable.image_nine)
                }
                else -> {
                    ImageCard(imageResId = R.drawable.image_ten)
                }
            }
        }
        DottedPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(6.dp)
                .weight(0.05f)
        )
    }
}