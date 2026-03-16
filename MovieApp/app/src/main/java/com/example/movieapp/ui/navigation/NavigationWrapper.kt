package com.example.movieapp.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.movieapp.R


@Composable
fun BottomNavigationBar(
    onHomeClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    BottomAppBar{
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { onHomeClick() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = stringResource(R.string.popular_tab)
                )
            }
            IconButton(onClick = { onFavoritesClick() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_heart),
                    contentDescription = stringResource(R.string.favorite_tab)
                )
            }
        }
    }
}

@Composable
fun MainScaffoldNavigation() {

    val backStack = rememberNavBackStack(Home)

    Scaffold(

        bottomBar = {
            BottomNavigationBar(
                onHomeClick = { backStack.add(Home) },
                onFavoritesClick = { backStack.add(Route.Favorites) }
            )
        }
    ) { paddingValues ->

        NavDisplay(
            modifier = Modifier.padding(paddingValues),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = { key ->
                when (key) {

                    is Home -> NavEntry(key) {


                        HomeView(
                            onMovieClick = { movie -> backStack.add(Route.Details(movie.id)) },
                        )
                    }

                    is Route.Details -> NavEntry(key) {

                        val detailViewModel: DetailViewModel = hiltViewModel()
                        val movieId = key.id

                        LaunchedEffect(movieId) {
                            detailViewModel.getMovie(movieId)
                        }

                        DetailsView(
                            backStack = backStack
                        )

                    }

                    is Route.Favorites -> NavEntry(key) {

                        FavoritesView(
                            onMovieClick = { backStack.add(Route.Details(it.id)) },
                            onDiscoverClick = { backStack.removeLastOrNull()}
                        )
                    }

                    else -> NavEntry(key = key) {}
                }
            }
        )
    }
}