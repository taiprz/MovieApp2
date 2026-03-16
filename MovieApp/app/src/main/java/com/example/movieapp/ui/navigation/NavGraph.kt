package com.example.movieapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.movieapp.data.utils.Route
import com.example.movieapp.data.utils.Route.Home
import com.example.movieapp.ui.details.DetailViewModel
import com.example.movieapp.ui.details.DetailsView
import com.example.movieapp.ui.favorites.FavoritesView
import com.example.movieapp.ui.home.HomeView
import com.example.movieapp.ui.search.SearchScreen
import com.example.movieapp.R
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource

@Composable
fun BottomNavigationBar(
    hazeState: HazeState,
    onHomeClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(70.dp)
            .hazeChild(state = hazeState, shape = CircleShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.01f),
                        Color.White.copy(alpha = 0.01f)
                    )
                ),
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.8f),
                        Color.White.copy(alpha = 0.2f)
                    )
                ),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onHomeClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_home),
                    contentDescription = "Home",
                    tint = Color(0xFF564787)
                )
            }

            IconButton(onClick = onSearchClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_magnifying_glass),
                    contentDescription = "Search",
                    tint = Color(0xFFf9b5ac)
                )
            }

            IconButton(onClick = onFavoritesClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_heart),
                    contentDescription = "Favorites",
                    tint = Color(0xFFf9b5ac)
                )
            }
        }
    }
}

@Composable
fun MainScaffoldNavigation() {

    val backStack = rememberNavBackStack(Home)
    val hazeState = remember { HazeState() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFf9b5ac),
                        Color(0xFFd0d6b5),
                        Color(0xff9dbf9e),
                        Color(0xff987284)
                    )
                )
            )
    ) {

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                BottomNavigationBar(
                    hazeState = hazeState,
                    onHomeClick = { backStack.add(Home) },
                    onFavoritesClick = { backStack.add(Route.Favorites) },
                    onSearchClick = { backStack.add(Route.Search) }
                )
            }
        ) { paddingValues ->

            NavDisplay(
                modifier = Modifier
                    .padding(paddingValues)
                    .haze(
                        hazeState,
                        blurRadius = 50.dp,
                        tint = Color.White.copy(alpha = 0.08f),
                        noiseFactor = 0.02f,
                        backgroundColor = Color.Transparent
                    ),
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = { key ->

                    when (key) {

                        is Home -> NavEntry(key) {
                            HomeView(
                                onMovieClick = {
                                    backStack.add(Route.Details(it.id))
                                }
                            )
                        }

                        is Route.Details -> NavEntry(key) {
                            val detailViewModel: DetailViewModel = hiltViewModel()

                            LaunchedEffect(key.id) {
                                detailViewModel.getMovie(key.id)
                            }

                            DetailsView(detailViewModel, backStack)
                        }

                        is Route.Favorites -> NavEntry(key) {
                            FavoritesView(
                                onMovieClick = {
                                    backStack.add(Route.Details(it.id))
                                },
                                onDiscoverClick = {
                                    backStack.removeLastOrNull()
                                },
                                onSearchClick = {
                                    backStack.add(Route.Search)
                                }
                            )
                        }

                        is Route.Search -> NavEntry(key) {
                            SearchScreen(
                                onMovieClick = {
                                    backStack.add(Route.Details(it.id))
                                },
                                onDiscoverClick = {
                                    backStack.removeLastOrNull()
                                }
                            )
                        }

                        else -> NavEntry(key) {}
                    }
                }
            )
        }
    }
}
