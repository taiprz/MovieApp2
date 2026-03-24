package com.example.movieapp.ui.details

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.movieapp.domain.model.Cast
import com.example.movieapp.domain.model.Credits
import com.example.movieapp.domain.model.Crew
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.theme.Poppins

@Composable
fun DetailsView(
    detailsvm: DetailViewModel = hiltViewModel(),
    backStack: NavBackStack<NavKey>,
    movieID: Int
) {
    val detailState by detailsvm.detailsState.collectAsStateWithLifecycle()
    val creditsState by detailsvm.creditsState.collectAsStateWithLifecycle()
    var showSheet by remember { mutableStateOf(false) }

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
            onRemoveFavorite = { detailsvm.removeFavorite(movie) },
            onSeeCredits = {
                detailsvm.getMovieCredits(movie.id)
                showSheet = true
            },
            showSheet = showSheet,
            onDismissSheet = { showSheet = false },
            creditsState = creditsState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsViewContent(
    movie: Movie,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onAddFavorite: () -> Unit,
    onRemoveFavorite: () -> Unit,
    onSeeCredits: () -> Unit,
    showSheet: Boolean,
    onDismissSheet: () -> Unit,
    creditsState: CreditsState
) {
    val context = LocalContext.current

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

            Spacer(modifier = Modifier.height(12.dp))

            GenresDisplay(movie)

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

            DetailsActionButtons(
                movie = movie,
                onSeeCredits = onSeeCredits,
                onShare = { text ->
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }

                    val chooser = Intent.createChooser(intent, "Share via")
                    context.startActivity(chooser)

                }
            )
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissSheet,
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            creditsState.credits?.let { credits ->
                CreditsContent(
                    cast = credits.cast,
                    crew = credits.crew
                )
            }
        }
    }
}

@Composable
fun CreditsContent(cast: List<Cast>, crew: List<Crew>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        if (cast.isNotEmpty()) {
            item {
                Text(
                    text = "Cast",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    fontFamily = Poppins
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(cast) { cast ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        AsyncImage(
                            model = cast.profilePath,
                            contentDescription = cast.name,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(50))
                                .border(
                                    2.dp,
                                    Color.White.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(50)
                                ),
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.ic_no_image)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = cast.name,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Poppins,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "as ${cast.character}",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                                fontFamily = Poppins
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (crew.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.crew),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    fontFamily = Poppins
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(crew) { crew ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        AsyncImage(
                            model = crew.profilePath,
                            contentDescription = crew.name,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(50))
                                .border(
                                    2.dp,
                                    Color.White.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(50)
                                ),
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.ic_no_image)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = crew.name,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Poppins,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = crew.job,
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                                fontFamily = Poppins
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreditsButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black.copy(alpha = 0.7f),
            contentColor = Color.White
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = SolidColor(Color.White.copy(alpha = 0.3f))
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        ),
        modifier = modifier
            .height(50.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = null,
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.see_credits),
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold
            )
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

        val title = if (isFavorite) stringResource(R.string.remove_from_favorites)
        else stringResource(R.string.add_to_favorites)

        val message = if (isFavorite)
            stringResource(R.string.this_movie_will_be_removed_from_your_favorites)
        else
            stringResource(R.string.do_you_want_to_save_this_movie_to_your_favorites)

        val confirmColor = if (isFavorite) Color.Red else Color.Gray

        AlertDialog(
            onDismissRequest = { showDialog = false },
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,

            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(if (isFavorite) R.drawable.ic_remove else R.drawable.ic_heart),
                        contentDescription = stringResource(R.string.save_or_delete_from_favorites),
                        tint = confirmColor,
                        modifier = Modifier
                            .padding(end = 8.dp)
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
                        stringResource(R.string.confirm),
                        color = confirmColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },

            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun GenresDisplay(movie: Movie) {
    Row(modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("")
            Text("Action")
            Text("Action")
        }
}

@Composable
fun DetailsActionButtons(
    movie: Movie,
    onSeeCredits: () -> Unit,
    onShare: (String) -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CreditsButton(
            onClick = onSeeCredits,
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = {
                val movieUrl = "https://www.themoviedb.org/movie/${movie.id}"
                onShare(movieUrl)
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black.copy(alpha = 0.75f),
                contentColor = Color.White
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = SolidColor(Color.White.copy(alpha = 0.25f))
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 2.dp
            ),
            modifier = Modifier
                .height(50.dp)
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_share),
                    contentDescription = "Share",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Share",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
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
        genreIds = listOf(28, 878, 12)
    )

    val sampleCredits = Credits(
        cast = listOf(
            Cast(
                adult = false,
                castId = 1,
                character = "Dom Cobb",
                creditId = "52fe4532c3a368484e04df11",
                gender = 2,
                id = 6193,
                knownForDepartment = "Acting",
                name = "Leonardo DiCaprio",
                order = 0,
                originalName = "Leonardo DiCaprio",
                popularity = 10.0,
                profilePath = "/wo2hJpn04vbtmh0B9utCFdsQhxM.jpg"
            ),
            Cast(
                adult = false,
                castId = 2,
                character = "Arthur",
                creditId = "52fe4532c3a368484e04df15",
                gender = 2,
                id = 24045,
                knownForDepartment = "Acting",
                name = "Joseph Gordon-Levitt",
                order = 1,
                originalName = "Joseph Gordon-Levitt",
                popularity = 8.0,
                profilePath = "/4U9G4YwTlIEb7vDcpXvXknb6fQp.jpg"
            )
        ),
        crew = listOf(
            Crew(
                adult = false,
                creditId = "52fe4532c3a368484e04df25",
                department = "Directing",
                gender = 2,
                id = 525,
                job = "Director",
                knownForDepartment = "Directing",
                name = "Christopher Nolan",
                originalName = "Christopher Nolan",
                popularity = 9.0,
                profilePath = "/cLH6q5fJ0XCMfKMX6FJ6ZtFw5Gp.jpg"
            )
        ),
        id = 1
    )

    DetailsViewContent(
        movie = sampleMovie,
        isFavorite = true,
        onBackClick = {},
        onAddFavorite = {},
        onRemoveFavorite = {},
        onSeeCredits = {},
        showSheet = false,
        onDismissSheet = {},
        creditsState = CreditsState(
            credits = sampleCredits,
            isLoading = false
        )
    )
}