package com.example.movieapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.R
import com.example.movieapp.ui.theme.DarkPink
import com.example.movieapp.ui.theme.LightPink
import com.example.movieapp.ui.theme.PetalFrost

@Preview
@Composable
fun Searchbar() {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember {
        FocusRequester()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .shadow(ambientColor = Color(0xFFf9b5ac),
                spotColor = Color(0xff987284),
                elevation = if (isFocused) 15.dp else 0.dp,
                clip = true,
                shape = CircleShape),
        shape = CircleShape
    ) {
        BasicTextField(
            value = "Search movies...",
            onValueChange = {  },
            interactionSource = null,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    brush = Brush
                        .horizontalGradient(listOf
                            (Color(0xff987284),
                            Color(0xff9dbf9e))
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
    )
    {
//        BottomBarPrv()

        MovieItem()
    }
}



@Preview
@Composable
fun MovieItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
            .padding(36.dp)
            .background(Color.Black)
    )
     {

     }
}

@Preview
@Composable
fun TextPrvw() {
    FontFormat("Popular Movies")
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

@Preview
@Composable
fun BottomBarPrv() {
    BottomAppBar{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape),
            horizontalArrangement = Arrangement.SpaceEvenly


        ) {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = stringResource(R.string.popular_tab)
                )
            }
            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(R.drawable.ic_heart),
                    contentDescription = stringResource(R.string.favorite_tab)
                )
            }
        }
    }
}



