package com.example.movieapp.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.home.MovieListEvents
import com.example.movieapp.ui.theme.Poppins

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit,
    onDiscoverClick: () -> Unit
) {

    var searchText by rememberSaveable { mutableStateOf("") }

    val movies = searchViewModel.moviesFound.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        AppName()

        Spacer(modifier = Modifier.height(12.dp))

        FontFormat("Search Movies")

        Spacer(modifier = Modifier.height(16.dp))

        Searchbar(
            searchText = searchText, onSearchTextChange = { text ->
                searchText = text
                searchViewModel.onEvent(
                    MovieListEvents.Search(text)
                )
            })

        Spacer(modifier = Modifier.height(20.dp))

        SearchMovieList(
            movies = movies, onMovieClick = onMovieClick
        )
    }
}

@Composable
fun Searchbar(
    searchText: String, onSearchTextChange: (String) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                ambientColor = Color(0xFFf9b5ac),
                spotColor = Color(0xff987284),
                elevation = if (isFocused) 15.dp else 0.dp,
                shape = CircleShape,
                clip = true
            ), shape = CircleShape
    ) {

        TextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            singleLine = true,
            interactionSource = interactionSource,

            placeholder = {
                Text(
                    "Enter movie title", fontFamily = Poppins
                )
            },

            textStyle = TextStyle(
                fontFamily = Poppins
            ),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),

            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp, brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xff987284), Color(0xff9dbf9e)
                        )
                    ), shape = CircleShape
                )
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun SearchMovieList(
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

                SearchMovieItem(
                    movie = movie,
                    onMovieClick = onMovieClick
                )
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
private fun SearchMovieItem(
    movie: Movie,
    onMovieClick: (Movie) -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .border(
                border = BorderStroke(
                    8.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xff838E83), Color(0xFFf9b5ac), Color(0xFF564787)
                        )
                    ),
                ), shape = RoundedCornerShape(16.dp)
            )
            .aspectRatio(2f / 3f)
            .clickable { onMovieClick(movie) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        AsyncImage(
            model = movie.posterPath,
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}


@Composable
private fun AppName() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        FontFormat("Movi3 Arch1ve")
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




