package com.example.movieapp.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.data.utils.Route.Home
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.theme.Poppins

@Composable
fun DetailsView(
    detailsvm: DetailViewModel = hiltViewModel(),
    backStack: NavBackStack<NavKey>,
    movieID: Int
) {
    val detailState by detailsvm.detailsState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        detailsvm.getMovie(movieID)
    }

    detailState.movie?.let { movie ->
        val isFavorite = detailState.isFavorite
        DetailsViewContent(
            movie = movie,
            isFavorite = isFavorite,
            onBackClick = { backStack.removeLastOrNull() },
            onAddFavorite = { detailsvm.addToFavorites(movie) },
            onRemoveFavorite = { detailsvm.removeFavorite(movie) }
        )
    }
}

@Composable
fun DetailsViewContent(
    movie: Movie,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onAddFavorite: () -> Unit,
    onRemoveFavorite: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = movie.posterPath,
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.TopCenter),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BackButton(onBackClick)
                        FavoriteButton(isFavorite, onAddFavorite, onRemoveFavorite)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = movie.title,
                fontFamily = Poppins,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_calendar),
                    contentDescription = stringResource(R.string.release_date),
                    modifier = Modifier.size(18.dp)
                )

                Text(movie.releaseDate)

                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = stringResource(R.string.popularity),
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(18.dp)
                )

                if (movie.voteAverage == 0.0) {
                    Text(stringResource(R.string.unavailable))
                } else {
                    Text("${movie.voteAverage}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.overview,
                fontFamily = Poppins,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

        }
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = stringResource(R.string.return_button),
            tint = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.size(20.dp)
        )
    }
}


@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onAddFavorite: () -> Unit,
    onRemoveFavorite: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val iconTint = if (isFavorite) Color.White else Color.White.copy(alpha = 0.6f)

    IconButton(onClick = { showDialog = true }) {
        Icon(
            painter = painterResource(R.drawable.ic_heart),
            contentDescription = stringResource(R.string.favorite_button),
            tint = iconTint,
            modifier = Modifier.size(22.dp)
        )
    }

    if (showDialog) {

        val title = if (isFavorite) "Remove from favorites"
        else "Add to favorites"

        val message = if (isFavorite)
            "This movie will be removed from your favorites."
        else
            "Do you want to save this movie to your favorites?"

        val confirmColor = if (isFavorite) Color.Red else Color.Black

        AlertDialog(
            onDismissRequest = { showDialog = false },
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,

            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(if (isFavorite) R.drawable.ic_remove else R.drawable.ic_heart),
                        contentDescription = "Save or delete from favorites",
                        tint = confirmColor,
                        modifier = Modifier.padding(end = 8.dp)
                            .size(24.dp)
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },

            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        if (isFavorite) onRemoveFavorite() else onAddFavorite()
                    }
                ) {
                    Text(
                        text = "Confirm",
                        color = confirmColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },

            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsViewPreview() {

    val sampleMovie = Movie(
        id = 1,
        title = "Inception",
        overview = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
        adult = false,
        backdropPath = "https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg",
        posterPath = "https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg",
        originalLanguage = "en",
        originalTitle = "Inception",
        popularity = 82.3,
        releaseDate = "2010-07-16",
        voteAverage = 8.3,
        voteCount = 22186,
        video = false,
        category = "Popular",
        genreIds = listOf("28", "878", "12")
    )

    DetailsViewContent(
        movie = sampleMovie,
        isFavorite = true,
        onBackClick = {},
        onAddFavorite = {},
        onRemoveFavorite = {}
    )
}