package com.example.movieapp.ui.details

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.theme.Parchment
import com.example.movieapp.ui.theme.PetalFrost


// CHANGES: CHANGED WHERE PARAMETERS ARE RECEIVED TO A SUPERIOR LEVEL
// DIVIDED SOME OF THE COMPONENT INTO SMALLER PIECES

@Composable
fun DetailsView(
    detailsvm: DetailViewModel = hiltViewModel(),
    backStack: NavBackStack<NavKey>
) {

    val detailState by detailsvm.detailsState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    )
    {

        when {
            detailState.isLoading -> {
                CircularProgressIndicator()
            }
        }

        detailState.movie?.let { movie ->

            val isFavorite = detailState.isFavorite

            MovieDetails(
                movie = movie,
                isFavorite = isFavorite,
                poster = movie.posterPath,
                onBackClick = {
                    backStack.removeLastOrNull()
                },
                onAddFavorite = { detailsvm.addToFavorites(movie) },
                onRemoveFavorite = { detailsvm.removeFavorite(movie) })
        }
    }
}

@Composable
fun MovieDetails(
    movie: Movie,
    isFavorite: Boolean,
    poster: Any?,
    onBackClick: () -> Unit,
    onAddFavorite: () -> Unit,
    onRemoveFavorite: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Parchment)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Box(modifier = Modifier.aspectRatio(2 / 3f)) {
            AsyncImage(
                model = poster,
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight
            )
        }

        MovieInfo(movie)

        ButtonsPanel(
            isFavorite = isFavorite,
            onBackClick = onBackClick,
            onAddFavorite = onAddFavorite,
            onRemoveFavorite = onRemoveFavorite
        )
    }
}

@Composable
fun MovieInfo(movie: Movie) {
    Column(horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            text = movie.title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier
                .padding(8.dp)
        )

        Text(
            movie.overview,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                Alignment.CenterHorizontally
            )
        )
        {

            Icon(
                painterResource(R.drawable.ic_calendar),
                stringResource(R.string.calendar_icon),
                modifier = Modifier
                    .size(16.dp)
            )

            FontFormat(movie.releaseDate)

            VerticalDivider()

            Icon(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = stringResource(R.string.vote_average),
                modifier = Modifier
                    .size(16.dp)
            )

            FontFormat(movie.voteAverage.toString())
        }
    }
}

@Composable
fun ButtonsPanel(
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onAddFavorite: () -> Unit,
    onRemoveFavorite: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        BackButton(onClick = onBackClick)

        Spacer(modifier = Modifier.width(16.dp))

        FavoriteButton(
            isFavorite = isFavorite,
            onAddFavorite = onAddFavorite,
            onRemoveFavorite = onRemoveFavorite
        )
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {

    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = stringResource(R.string.return_button),
            tint = Color.Black
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

    val iconTint = if (isFavorite) PetalFrost else Color.Black

    IconButton(onClick = { showDialog = true }) {
        Icon(
            painter = painterResource(R.drawable.ic_heart),
            contentDescription = stringResource(R.string.favorite_button),
            tint = iconTint
        )
    }

    if (showDialog) {

        val dialogTitle = if (isFavorite)
            stringResource(R.string.delete_movie)
        else
            stringResource(R.string.save_movie)

        val dialogText = if (isFavorite)
            stringResource(R.string.delete_from_favorites)
        else
            stringResource(R.string.add_to_favorites)

        AlertDialog(
            title = { Text(dialogTitle) },
            text = { Text(dialogText) },

            onDismissRequest = { showDialog = false },

            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        if (isFavorite) onRemoveFavorite()
                        else onAddFavorite()
                    }
                ) {
                    Text(stringResource(R.string.confirm))
                }
            },

            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text(stringResource(R.string.dismiss))
                }
            }
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
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis,
    )
}


@Preview(showBackground = true)
@Composable
fun Prvw() {
//    details(
//        movie = Movie(
//            id = 2,
//            title = "Preview Movie",
//            overview = " This is just preview data. This is just preview data. This is just preview data. This is just preview data. This is just preview data. This is just preview data. This is just preview data. This is just preview data. This is just preview data. This is just preview data.",
//            adult = false,
//            backdropPath = "/qhyyyWrHUbl6QG4udAJj17CBa5.jpg",
//            originalLanguage = "en",
//            originalTitle = "Preview Movie",
//            popularity = 0.0,
//            posterPath = "/qhyyyWrHUbl6QG4udAJj17CBa5.jpg",
//            releaseDate = "2024-01-01",
//            voteAverage = 5.365,
//            voteCount = 100,
//            video = false,
//            category = "POPULAR",
//            genreIds = listOf("Action, Suspense")
//        )
//    )

    Column(
        modifier = Modifier
            .background(color = Color.White)
    ) {
        MovieInfo(
            movie = Movie(
                id = 2,
                title = "Preview Movie",
                overview = " This is just preview data. This is just preview data. This is just preview data. This is just preview data. This is just preview data. This is just preview data. This is just preview data. This is just preview data. This is just preview data. This is just preview data.",
                adult = false,
                backdropPath = "/qhyyyWrHUbl6QG4udAJj17CBa5.jpg",
                originalLanguage = "en",
                originalTitle = "Preview Movie",
                popularity = 0.0,
                posterPath = "/qhyyyWrHUbl6QG4udAJj17CBa5.jpg",
                releaseDate = "2025-02-11",
                voteAverage = 5.365,
                voteCount = 100,
                video = false,
                category = "POPULAR",
                genreIds = listOf("Action, Suspense")
            )
        )
    }
}