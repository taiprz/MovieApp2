package com.example.movieapp.ui.login

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieapp.R
import com.example.movieapp.ui.components.font.AppName
import com.example.movieapp.ui.theme.Poppins

@Composable
fun LoginView(loginvm: LoginViewModel = hiltViewModel()) {
    LoginViewContent()
}


@Composable
fun LoginViewContent() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(52.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppName()

            LoginTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(R.string.email)
            )

            LoginTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.password),
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoginButton(
                onClick = {

                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = stringResource(R.string.sign_up),
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEE7674),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun LoginButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEE7674),
            contentColor = Color.White
        ),
        modifier = modifier
            .height(55.dp)
            .shadow(8.dp, RoundedCornerShape(50))
    ) {
        Text(
            text = stringResource(R.string.log_in),
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFFee7674) else Color(0xFF987284).copy(alpha = 0.4f)
    )
    val borderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 1.dp
    )
    val elevation by animateDpAsState(
        targetValue = if (isFocused) 12.dp else 4.dp
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = elevation, shape = RoundedCornerShape(30.dp))
            .border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(30.dp)),
        shape = RoundedCornerShape(30.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            label = { Text(label, fontFamily = Poppins) },
            placeholder = { Text(label, fontFamily = Poppins) },
            interactionSource = interactionSource,
            textStyle = TextStyle(fontFamily = Poppins),
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Email
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    LoginViewContent()
}


