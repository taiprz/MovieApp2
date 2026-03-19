package com.example.movieapp.ui.components.font

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.movieapp.ui.theme.Poppins

@Composable
 fun FontFormat(
    text: String, modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = Poppins,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xff838E83), Color(0xFFf9b5ac), Color(0xFF564787)
                )
            )
        )
    )
}

@Preview
@Composable
private fun FontFormatPreview() {
    FontFormat("Preview")
}
