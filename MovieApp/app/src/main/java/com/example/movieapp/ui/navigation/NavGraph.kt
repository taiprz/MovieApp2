package com.example.movieapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.movieapp.data.utils.Route
import com.example.movieapp.data.utils.Route.*
import com.example.movieapp.ui.favorites.FavoritesView
import com.example.movieapp.ui.home.HomeView
import com.example.movieapp.ui.search.SearchScreen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.ui.details.DetailsView
import com.example.movieapp.ui.login.LoginView

@Composable
fun MainScaffoldNavigation() {

    val backStack = rememberNavBackStack(Login)
    val hazeState = remember { HazeState() }

    val currentEntry = backStack.lastOrNull()

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
                BottomBar(
                    hazeState = hazeState,
                    currentRoute = currentEntry as Route,
                    onHomeClick = { backStack.add(Home) },
                    onFavoritesClick = { backStack.add(Favorites) },
                    onSearchClick = { backStack.add(Search) }
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

                        is Login -> NavEntry(key) {
                            LoginView()
                        }


                        is Home -> NavEntry(key) {
                            HomeView(
                                onMovieClick = {
                                    backStack.add(Details(it.id))
                                })
                        }

                        is Details -> NavEntry(key) {
                            DetailsView(backStack = backStack, movieID = key.id)
                        }

                        is Favorites -> NavEntry(key) {
                            FavoritesView(
                                onMovieClick = { backStack.add(Details(it.id)) },
                                onDiscoverClick = { backStack.add(Home) }
                            )
                        }

                        is Search -> NavEntry(key) {
                            SearchScreen(
                                onMovieClick = { backStack.add(Details(it.id)) },
                            )
                        }
                        else -> NavEntry(key) {}
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    val hazeState = remember { HazeState() }
    BottomBar(
        hazeState = hazeState,
        currentRoute = Home,
        onHomeClick = {},
        onFavoritesClick = {},
        onSearchClick = {}
    )
}