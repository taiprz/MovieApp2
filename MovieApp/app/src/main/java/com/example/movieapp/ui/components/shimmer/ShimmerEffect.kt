package com.example.movieapp.ui.components.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun Modifier.shimmerEffect(): Modifier {
    val transition = rememberInfiniteTransition(label = "")

    val shimmerTranslate by transition.animateFloat(
        initialValue = -800f,
        targetValue = 1600f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1800,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    val shimmerColors = listOf(
        Color(0xFFB0B0B0).copy(alpha = 0.6f),
        Color(0xFFE0E0E0).copy(alpha = 0.9f),
        Color(0xFFB0B0B0).copy(alpha = 0.6f),
    )

    return this.drawBehind {
        val brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(shimmerTranslate, 0f),
            end = Offset(shimmerTranslate + 400f, size.height)
        )
        drawRect(brush)
    }
}