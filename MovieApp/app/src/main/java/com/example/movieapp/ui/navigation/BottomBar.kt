package com.example.movieapp.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.R
import com.example.movieapp.data.utils.Route
import com.example.movieapp.data.utils.Route.Favorites
import com.example.movieapp.data.utils.Route.Home
import com.example.movieapp.data.utils.Route.Search
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild

@Composable
fun BottomBar(
    hazeState: HazeState,
    currentRoute: Route?,
    onHomeClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onSearchClick: () -> Unit
) {

    fun iconColor(isSelected: Boolean): Color =
        if (isSelected) Color.White.copy(alpha = 0.9f) else Color.White.copy(alpha = 0.5f)

    val isHomeSelected = currentRoute is Home
    val isSearchSelected = currentRoute is Search
    val isFavSelected = currentRoute is Favorites

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .hazeChild(
                state = hazeState, shape = CircleShape
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.05f), Color.White.copy(alpha = 0.02f)
                    )
                ), shape = CircleShape
            )
            .border(
                width = 1.dp, brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.6f), Color.White.copy(alpha = 0.2f)
                    )
                ), shape = CircleShape
            ), contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        )
        {

            BarIcon(
                onHomeClick,
                iconRes = R.drawable.ic_home,
                contentDes = stringResource(R.string.home),
                tint = iconColor(isHomeSelected)
            )
            BarIcon(
                onSearchClick,
                iconRes = R.drawable.ic_magnifying_glass,
                contentDes = stringResource(R.string.search),
                tint = iconColor(isSearchSelected)
            )
            BarIcon(
                onFavoritesClick,
                iconRes = R.drawable.ic_heart,
                contentDes = stringResource(R.string.favorites),
                tint = iconColor(isFavSelected)
            )
        }
    }
}

@Composable
fun BarIcon(onClick: () -> Unit, @DrawableRes iconRes: Int, contentDes: String, tint: Color) {
    IconButton(onClick) {
        Icon(
            painter = painterResource(iconRes),
            contentDes,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
    }
}
