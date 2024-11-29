package com.moguishio.moguishio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun LinearGradient() {
    val gradient = Brush.linearGradient(
        0.0f to Color.hsl(181F, 76F, 61F),
        500.0f to Color.hsl(242F, 88F, 38F),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    Box(modifier = Modifier.background(gradient))
}