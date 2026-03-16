package com.example.movieapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.theme.DustGrey
import com.example.movieapp.ui.theme.PetalFrost

@Composable
fun HomeView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit
) {
    val movies = homeViewModel.movies.collectAsLazyPagingItems()
    val moviesFound = homeViewModel.moviesFound.collectAsLazyPagingItems()

    var searchText by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFee7674),
                        Color(0xFFf9b5ac),
                        Color(0xFFd0d6b5),
                        Color(0xff9dbf9e),
                        Color(0xff987284)
                    )
                )
            )
            .padding(16.dp)
    ) {

        SearchBar(
            searchText = searchText,
            onSearchTextChange = {
                searchText = it
                homeViewModel.onEvent(MovieListEvents.Search(it))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

       

        if (searchText.isEmpty()) {
            MovieList(movies = movies, onMovieClick = onMovieClick)
        } else {
            MovieList(movies = moviesFound, onMovieClick = onMovieClick)
        }
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = { onSearchTextChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Search movies...") },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = PetalFrost,
            unfocusedContainerColor = PetalFrost,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun MovieList(
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies.itemCount) { index ->
            movies[index]?.let { movie ->
                MovieItem(
                    movie = movie,
                    onMovieClick = onMovieClick
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: (Movie) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = DustGrey)
            .clickable {
                onMovieClick(movie)
            }
            .padding(8.dp)) {

        AsyncImage(
            model = movie.posterPath,
            contentDescription = movie.title,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movie.title, style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = movie.overview,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun SearchbarPrv(function: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember {
        FocusRequester()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .shadow(
                ambientColor = Color(0xFFf9b5ac),
                spotColor = Color(0xff987284),
                elevation = if (isFocused) 15.dp else 0.dp,
                clip = true,
                shape = CircleShape
            ),
        shape = CircleShape
    ) {
        BasicTextField(
            value = "Search movies...",
            onValueChange = { },
            interactionSource = null,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    brush = Brush
                        .horizontalGradient(
                            listOf
                                (
                                Color(0xff987284),
                                Color(0xff9dbf9e)
                            )
                        ),
                    shape = CircleShape
                )
                .padding(16.dp)
                .background(Color.White)
                .focusRequester(focusRequester),
        )
    }
}

@Preview
@Composable
fun Background() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFee7674),
                        Color(0xFFf9b5ac),
                        Color(0xFFd0d6b5),
                        Color(0xff9dbf9e),
                        Color(0xff987284)
                    )
                )
            )
            .padding(16.dp)
    ) {
        SearchbarPrv() {

        }
    }
}







