package com.example.movieapp.ui.search

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.home.MovieListEvents
import com.example.movieapp.ui.theme.Poppins
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import com.example.movieapp.ui.components.shimmer.ShimmerMovieGrid


@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val hasSearched by searchViewModel.hasSearched.collectAsState()
    val movies = searchViewModel.moviesFound.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        AppName()
        Spacer(modifier = Modifier.height(12.dp))
        FontFormat(stringResource(R.string.search_movies))
        Spacer(modifier = Modifier.height(16.dp))

        Searchbar(
            searchText = searchText,
            onSearchTextChange = { text ->
                searchText = text
                searchViewModel.onEvent(MovieListEvents.Search(text))
            }
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

            movies.loadState.refresh is LoadState.Loading || movies.itemCount == 0 -> {
                ShimmerMovieGrid()
            }

            movies.itemCount == 0 -> {
                NoMatches()
            }

            else -> {
                SearchMovieList(movies = movies, onMovieClick = onMovieClick)
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
                SearchMovieItem(movie = movie, onMovieClick = onMovieClick)
            }
        }
    }
}

@Composable
private fun SearchMovieList2(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(12.dp)
    ) {
        items(movies) { movie ->
            SearchMovieItem(
                movie = movie,
                onMovieClick = onMovieClick
            )
        }
    }
}


@Composable
fun SearchMovieItem(
    movie: Movie,
    onMovieClick: (Movie) -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .aspectRatio(2f / 3f)
            .clickable { onMovieClick(movie) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        AsyncImage(
            model = movie.posterPath,
            placeholder = painterResource(R.drawable.ic_no_image),
            error = painterResource(R.drawable.ic_no_image),
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun AppName() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        FontFormat(stringResource(R.string.movi3_arch1ve))
    }
}

@Composable
private fun FontFormat(
    text: String, modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = Poppins,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xff838E83), Color(0xFFf9b5ac), Color(0xFF564787)
                )
            )
        )
    )
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
        FontFormat(stringResource(R.string.no_matches_found))
    }
}

@Preview
@Composable
fun Background() {

    val sampleMovies: List<Movie> = listOf(
        Movie(
            id = 1,
            title = "Inception",
            originalTitle = "Inception",
            originalLanguage = "en",
            overview = "",
            popularity = 82.3,
            posterPath = "https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg",
            backdropPath = "",
            adult = false,
            video = false,
            voteAverage = 8.3,
            voteCount = 22186,
            genreIds = listOf("28", "878", "12"),
            releaseDate = "2010-07-16",
            category = "Popular"
        ),
        Movie(
            id = 2,
            title = "The Matrix",
            originalTitle = "The Matrix",
            originalLanguage = "en",
            overview = "",
            popularity = 77.5,
            posterPath = "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg",
            backdropPath = "",
            adult = false,
            video = false,
            voteAverage = 8.1,
            voteCount = 19730,
            genreIds = listOf("28", "878"),
            releaseDate = "1999-03-31",
            category = "Popular"
        )
    )

    Column(
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
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        AppName()
        Searchbar("Text") {}

        SearchMovieList2(sampleMovies, onMovieClick = {})

        NoMatches()

//        val sampleMovies =
//            listOf<Movie>(
//                id = 1,
//                title = "Inception",
//                originalTitle = "Inception",
//                originalLanguage = "en",
//                overview = "",
//                popularity = 82.3,
//                posterPath = "https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg",
//                backdropPath = "",
//                adult = false,
//                video = false,
//                voteAverage = 8.3,
//                voteCount = 22186,
//                genreIds = listOf("28", "878", "12"),
//                releaseDate = "2010-07-16",
//                category = "Popular"
//            )
//        Movie(
//            id = 2,
//            title = "The Matrix",
//            originalTitle = "The Matrix",
//            originalLanguage = "en",
//            overview = "",
//            popularity = 77.5,
//            posterPath = "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg",
//            backdropPath = "",
//            adult = false,
//            video = false,
//            voteAverage = 8.1,
//            voteCount = 19730,
//            genreIds = listOf("28", "878"),
//            releaseDate = "1999-03-31",
//            category = "Popular"
//        )
    }
}




