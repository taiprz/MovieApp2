package com.example.movieapp.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.movieapp.ui.theme.Poppins

@Composable
fun HomeView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit,
) {
    val popularMovies = homeViewModel.popularMovies.collectAsLazyPagingItems()
    val upcomingMovies = homeViewModel.upcomingMovies.collectAsLazyPagingItems()
    val topRatedMovies = homeViewModel.topRatedMovies.collectAsLazyPagingItems()
    val nowPlaying = homeViewModel.nowPlayingMovies.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {

        AppName()

        Popular()
        MovieList(movies = popularMovies, onMovieClick = onMovieClick)

        Upcoming()
        MovieList(movies = upcomingMovies, onMovieClick = onMovieClick)

        TopRated()
        MovieList(movies = topRatedMovies, onMovieClick = onMovieClick)

        NowPlaying()
        MovieList(movies = nowPlaying, onMovieClick = onMovieClick)
    }
}

@Composable
fun MovieList(
    movies: LazyPagingItems<Movie>, onMovieClick: (Movie) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        items(movies.itemCount) { index ->
            movies[index]?.let { movie ->
                MovieItem(
                    movie = movie, onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie, onMovieClick: (Movie) -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .aspectRatio(2f / 3f)
            .clickable { onMovieClick(movie) },
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    )
    {
        AsyncImage(
            model = movie.posterPath,
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
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
        Popular()
        MoviesList()
//        MovieItemPrv()
        Upcoming()
    }
}

@Composable
private fun AppName() {
    Text(
        text = "Movi3 Arch1ve",
        modifier = Modifier.fillMaxWidth(),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
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
fun Popular() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        FontFormat("Popular")

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(R.drawable.ic_star),
            contentDescription = "Popular icon",
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun Upcoming() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        FontFormat("Upcoming")

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(R.drawable.ic_coming),
            contentDescription = "Uncoming movies",
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun TopRated() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        FontFormat("Top Rated")

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(R.drawable.ic_top_rated),
            contentDescription = "Uncoming movies",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun NowPlaying() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        FontFormat("Now Playing")

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(R.drawable.ic_play),
            contentDescription = "Now Playing",
            modifier = Modifier.size(12.dp)
        )
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
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun MoviesList() {

    val sampleMovies = listOf(
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
        ), Movie(
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

    LazyRow(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sampleMovies) { movie ->
            MovieItem(movie = movie, onMovieClick = {})
        }
    }
}

@Preview
@Composable
fun MovieItemPrv() {
    Card(
        modifier = Modifier
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
            .aspectRatio(2 / 1f), shape = RoundedCornerShape(16.dp)
    )
    {
    }
}










