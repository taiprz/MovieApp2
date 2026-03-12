package com.example.movieapp.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.data.utils.Route
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.home.MovieListState
import com.example.movieapp.ui.theme.Parchment
import com.example.movieapp.ui.theme.PetalFrost
import dagger.hilt.android.lifecycle.HiltViewModel
@Composable
fun FavoritesView(
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    navController : NavHostController
) {

    val editModeState by favoritesViewModel.editMode.collectAsStateWithLifecycle()
    val favMoviesState by favoritesViewModel.movieListState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Parchment)
            .padding(16.dp)
    ) {
        Header(
            movies = favMoviesState.favoriteMovieList,
            editMode = editModeState,
            onEditClick = { favoritesViewModel.toggleEditMode()},
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            favMoviesState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            favMoviesState.favoriteMovieList.isEmpty() -> {
                EmptyListView( onDiscoverClick = { navController.navigate(Route.Home.route) })
            }

            else -> {
                FavMovieList(
                    movies = favMoviesState.favoriteMovieList,
                    editMode = editModeState,
                    onRemoveFavorite = { movie -> favoritesViewModel.removeFavorite(movie) },
                    // TODO: FIX THIS ROUTE WITH NAVIGATION 3 
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
fun Header(
    movies: List<Movie>,
    editMode: Boolean,
    onEditClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.your_favorites),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        if (movies.isNotEmpty()) {
            EditButton(editMode, onEditClick)
        }
    }
}

@Composable
fun EditButton(editMode: Boolean, onEditClick: () -> Unit) {
    IconButton(
        onClick = onEditClick,
        modifier = Modifier
            .background(PetalFrost)
            .size(36.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_edit),
            contentDescription = stringResource(R.string.edit_button),
            modifier = Modifier.size(20.dp),
            tint = if (editMode) Parchment else Color.Black
        )
    }
}

@Composable
fun FavMovieList(
    movies: List<Movie>,
    editMode: Boolean,
    onRemoveFavorite: (Movie) -> Unit,
    onMovieClick: (Movie) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->
            MovieItem(
                movie = movie,
                editMode = editMode,
                poster = movie.posterPath,
                onRemoveFavorite = onRemoveFavorite,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    editMode: Boolean,
    poster: Any?,
    onRemoveFavorite: (Movie) -> Unit,
    onMovieClick: (Movie) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onMovieClick(movie) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            AsyncImage(
                model = poster,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
            )

            if (editMode) {
                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = { onRemoveFavorite(movie) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_remove),
                        contentDescription = stringResource(R.string.remove_from_favorites_button)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = movie.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EmptyListView(
    onDiscoverClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Parchment),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = R.drawable.ic_sad,
                contentDescription = stringResource(R.string.empty_list_screen),
                modifier = Modifier.size(120.dp)
            )

            Text(
                text = stringResource(R.string.no_movies_saved_yet),
                fontWeight = FontWeight.Bold
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = PetalFrost,
                    contentColor = Color.White
                ),
                onClick = onDiscoverClick
            ) {
                Text(stringResource(R.string.discover_movies))
            }
        }
    }
}