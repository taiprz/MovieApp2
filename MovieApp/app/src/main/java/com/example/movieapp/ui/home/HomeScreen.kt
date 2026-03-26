package com.example.movieapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.R
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.components.font.AppName
import com.example.movieapp.ui.components.font.FontFormat
import com.example.movieapp.ui.components.movieitem.MovieItem
import com.example.movieapp.ui.components.shimmer.ShimmerMovieRow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit,
) {
    val popularMovies = homeViewModel.popularMovies.collectAsLazyPagingItems()
    val upcomingMovies = homeViewModel.upcomingMovies.collectAsLazyPagingItems()
    val topRatedMovies = homeViewModel.topRatedMovies.collectAsLazyPagingItems()
    val nowPlaying = homeViewModel.nowPlayingMovies.collectAsLazyPagingItems()

    HomeViewContent(
        popularMovies = popularMovies,
        upcomingMovies = upcomingMovies,
        topRatedMovies = topRatedMovies,
        nowPlayingMovies = nowPlaying,
        onMovieClick = onMovieClick
    )
}

@Composable
fun HomeViewContent(
    popularMovies: LazyPagingItems<Movie>,
    upcomingMovies: LazyPagingItems<Movie>,
    topRatedMovies: LazyPagingItems<Movie>,
    nowPlayingMovies: LazyPagingItems<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        AppName()

        MovieSection(
            title = stringResource(R.string.popular),
            iconRes = R.drawable.ic_star,
            movies = popularMovies,
            onMovieClick = onMovieClick
        )

        MovieSection(
            title = stringResource(R.string.upcoming),
            iconRes = R.drawable.ic_coming,
            movies = upcomingMovies,
            onMovieClick = onMovieClick
        )

        MovieSection(
            title = stringResource(R.string.top_rated),
            iconRes = R.drawable.ic_top_rated,
            movies = topRatedMovies,
            onMovieClick = onMovieClick
        )

        MovieSection(
            title = stringResource(R.string.now_playing),
            iconRes = R.drawable.ic_play,
            movies = nowPlayingMovies,
            onMovieClick = onMovieClick
        )
    }
}

@Composable
fun MovieSection(
    title: String,
    iconRes: Int,
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Movie) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FontFormat(title,)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(iconRes),
            contentDescription = title,
            modifier = Modifier.size(18.dp)
        )
    }


    val isLoading = movies.loadState.refresh is LoadState.Loading
    val isEmpty = movies.itemCount == 0

    if (isLoading || isEmpty) {
        ShimmerMovieRow()
    } else {
        MovieList(movies = movies, onMovieClick = onMovieClick)
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    val fakeMovies = (1..5).map {
        Movie(
            id = it,
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
            genreIds = listOf(28, 878, 12),
            releaseDate = "2010-07-16",
            category = "Popular"
        )
    }

    val fakePaging = flowOf(PagingData.from(fakeMovies)).collectAsLazyPagingItems()

    HomeViewContent(
        popularMovies = fakePaging,
        upcomingMovies = fakePaging,
        topRatedMovies = fakePaging,
        nowPlayingMovies = fakePaging,
        onMovieClick = {}
    )
}













