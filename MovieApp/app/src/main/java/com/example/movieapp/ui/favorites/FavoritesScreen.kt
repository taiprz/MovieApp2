package com.example.movieapp.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.theme.Poppins

@Composable
fun FavoritesView(
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit,
    onDiscoverClick: () -> Unit
) {

    val editModeState by favoritesViewModel.editMode.collectAsStateWithLifecycle()
    val favMoviesState by favoritesViewModel.movieListState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        AppName()

        Header(
            movies = favMoviesState.favoriteMovieList,
            editMode = editModeState,
            onEditClick = { favoritesViewModel.toggleEditMode() },
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            favMoviesState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            favMoviesState.favoriteMovieList.isEmpty() -> {
                EmptyListView(onDiscoverClick = onDiscoverClick)
            }

            else -> {
                FavMovieList(
                    movies = favMoviesState.favoriteMovieList,
                    editMode = editModeState,
                    onRemoveFavorite = { movie -> favoritesViewModel.removeFavorite(movie) },
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
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        FontFormat(stringResource(R.string.your_favorites))

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
            .size(36.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_edit),
            contentDescription = stringResource(R.string.edit_button),
            modifier = Modifier.size(20.dp),
            tint = if (editMode) Color.Black else Color.Black.copy(alpha = 0.5f)
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
            FavoriteMovieItem(
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
private fun FavoriteMovieItem(
    movie: Movie,
    onMovieClick: (Movie) -> Unit,
    editMode: Boolean,
    poster: String,
    onRemoveFavorite: (Movie) -> Unit
) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .aspectRatio(2f / 3f)
            .clickable { onMovieClick(movie) }
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            AsyncImage(
                model = poster,
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        if (editMode) {
            IconButton(
                onClick = { onRemoveFavorite(movie) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(24.dp)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(50)
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_remove),
                    contentDescription = "Remove Favorite",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


@Composable
private fun AppName() {
    Text(
        text = stringResource(R.string.movi3_arch1ve),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
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
fun EmptyListView(
    onDiscoverClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_sad),
                contentDescription = stringResource(R.string.empty_list_screen),
                modifier = Modifier.size(120.dp),
                tint = Color.White.copy(alpha = 8f)
            )

            Text(
                text = stringResource(R.string.no_movies_saved_yet),
                fontWeight = FontWeight.Bold
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                onClick = onDiscoverClick
            ) {
                Text(stringResource(R.string.discover_movies))
            }
        }
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