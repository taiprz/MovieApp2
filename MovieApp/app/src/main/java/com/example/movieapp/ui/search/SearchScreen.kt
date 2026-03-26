package com.example.movieapp.ui.search

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.R
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.home.MovieListEvents
import com.example.movieapp.ui.theme.Poppins
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.movieapp.ui.components.font.AppName
import com.example.movieapp.ui.components.font.FontFormat
import com.example.movieapp.ui.components.movieitem.MovieItem
import com.example.movieapp.ui.components.shimmer.ShimmerMovieGrid
import kotlinx.coroutines.flow.flowOf


@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val hasSearched by searchViewModel.hasSearched.collectAsState()
    val movies = searchViewModel.moviesFound.collectAsLazyPagingItems()

    SearchScreenContent(
        searchText = searchText,
        hasSearched = hasSearched,
        movies = movies,
        onSearchTextChange = { text ->
            searchText = text
            searchViewModel.onEvent(MovieListEvents.Search(text))
        },
        onMovieClick = onMovieClick
    )
}

@Composable
fun SearchScreenContent(
    searchText: String,
    hasSearched: Boolean,
    movies: LazyPagingItems<Movie>,
    onSearchTextChange: (String) -> Unit,
    onMovieClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        AppName()

        Spacer(modifier = Modifier.height(12.dp))

        FontFormat(stringResource(R.string.search_movies),)

        Spacer(modifier = Modifier.height(16.dp))

        Searchbar(
            searchText = searchText,
            onSearchTextChange = onSearchTextChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        when {
            !hasSearched -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.type_something_to_search_a_movie))
                }
            }

            movies.loadState.refresh is LoadState.Loading -> {
                ShimmerMovieGrid()
            }

            movies.itemCount == 0 -> {
                NoMatches()
            }

            else -> {
                SearchMovieList(
                    movies = movies,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
fun Searchbar(
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFFee7674) else Color(0xFF987284).copy(alpha = 0.4f)
    )
    val borderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 1.dp
    )
    val elevation by animateDpAsState(
        targetValue = if (isFocused) 12.dp else 4.dp
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation,
                shape = CircleShape
            )
            .border(
                width = borderWidth,
                color = borderColor,
                shape = CircleShape
            ),
        shape = CircleShape
    ) {
        TextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            singleLine = true,
            interactionSource = interactionSource,
            placeholder = { Text(stringResource(R.string.enter_movie_title), fontFamily = Poppins) },
            textStyle = TextStyle(fontFamily = Poppins),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun SearchMovieList(
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(12.dp)
    ) {
        items(movies.itemCount) { index ->
            movies[index]?.let { movie ->
                MovieItem(movie = movie, onMovieClick = onMovieClick)
            }
        }
    }
}

@Composable
fun NoMatches() {
    Column(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_error),
            contentDescription = stringResource(R.string.error),
            modifier = Modifier
                .size(32.dp)
        )
        FontFormat(stringResource(R.string.no_matches_found),)
    }
}

    @Preview(showBackground = true)
    @Composable
    fun SearchScreenPreview() {

        val fakeMovies = listOf(1..6).map {
            Movie(
                id = 1,
                title = "Movie",
                originalTitle = "Movie",
                originalLanguage = "en",
                overview = "",
                popularity = 80.0,
                posterPath = "https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg",
                backdropPath = "",
                adult = false,
                video = false,
                voteAverage = 8.0,
                voteCount = 1000,
                genreIds = listOf(28),
                releaseDate = "2020-01-01",
                category = "Popular"
            )
        }

        val fakePaging = flowOf(PagingData.from(fakeMovies)).collectAsLazyPagingItems()

        SearchScreenContent(
            searchText = "Batman",
            hasSearched = true,
            movies = fakePaging,
            onSearchTextChange = {},
            onMovieClick = {}
        )
    }





