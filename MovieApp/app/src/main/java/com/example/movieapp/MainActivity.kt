package com.example.movieapp

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.ui.navigation.MainScaffoldNavigation
import com.example.movieapp.ui.splashcreen.SplashScreenViewModel
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