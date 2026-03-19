package com.example.movieapp.ui.components.font

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.R

@Composable
 fun AppName() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        FontFormat(stringResource(R.string.movi3_arch1ve))
    }
}

@Preview
@Composable
private fun AppNamePreview() {
    AppName()
}