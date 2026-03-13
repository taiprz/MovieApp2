package com.example.movieapp

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.movieapp.data.navigation.MainScaffoldNavigation
import com.example.movieapp.ui.home.HomeViewModel
import com.example.movieapp.ui.splashcreen.SplashScreenViewModel
import com.example.movieapp.ui.details.DetailsView
import com.example.movieapp.ui.favorites.FavoritesView
import com.example.movieapp.data.utils.Route
import com.example.movieapp.ui.home.HomeView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import kotlin.jvm.java

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    // TODO: CHANGE ROUTES
    private val splashScreenViewModel: SplashScreenViewModel by lazy {
        ViewModelProvider(this@MainActivity)[SplashScreenViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {
            setKeepOnScreenCondition { splashScreenViewModel.isSplashScreenVisible.value }
            setOnExitAnimationListener { splash ->
                val rotationAnimator = ValueAnimator.ofFloat(0f, 90f)
                rotationAnimator.duration = 1500
                rotationAnimator.addUpdateListener {
                    splash.iconView.rotation = it.animatedValue as Float
                }
                rotationAnimator.doOnEnd { splash.remove() }
                rotationAnimator.start()
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScaffoldNavigation()
        }
    }
}