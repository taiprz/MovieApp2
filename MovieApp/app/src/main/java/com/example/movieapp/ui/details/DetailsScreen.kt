package com.example.movieapp.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
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
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.theme.Poppins

@Composable
fun DetailsView(
    detailsvm: DetailViewModel = hiltViewModel(),
    backStack: NavBackStack<NavKey>
) {
    val detailState by detailsvm.detailsState.collectAsStateWithLifecycle()

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
                    contentDescription = "Release Date",
                    modifier = Modifier.size(18.dp)
                )

                Text(movie.releaseDate)

                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = "Popularity",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(18.dp)
                )

                if (movie.voteAverage == 0.0) {
                    Text("Unavailable")
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = movie.originalLanguage,
                    fontFamily = Poppins,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(12.dp)
                )

                HorizontalDivider(
                    modifier = Modifier
                        .size(8.dp)
                )

                Text(
                    text = movie.originalTitle,
                    fontFamily = Poppins,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(12.dp)
                )
            }
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
)
{
    var showDialog by remember { mutableStateOf(false) }
    val iconTint = if (isFavorite) Color.White else Color.White.copy(alpha = 0.5f)

    IconButton(onClick = { showDialog = true }) {
        Icon(
            painter = painterResource(R.drawable.ic_heart),
            contentDescription = stringResource(R.string.favorite_button),
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )
    }

    if (showDialog) {
        val dialogTitle = if (isFavorite) stringResource(R.string.delete_movie)
        else stringResource(R.string.save_movie)

        val dialogText = if (isFavorite) stringResource(R.string.delete_from_favorites)
        else stringResource(R.string.add_to_favorites)

        AlertDialog(
            title = { Text(dialogTitle) },
            text = { Text(dialogText) },
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        if (isFavorite) onRemoveFavorite() else onAddFavorite()
                    }) {
                    Text(stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.dismiss))
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