package com.accenture.mvi.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.core.view.WindowCompat
import com.accenture.mvi.presentation.model.Pokemon
import com.accenture.mvi.presentation.navigation.NavArgs
import com.accenture.mvi.presentation.navigation.Navigation
import com.accenture.mvi.presentation.ui.theme.PokemonMVITheme
import com.accenture.mvi.presentation.view.DetailScreen
import com.accenture.mvi.presentation.view.ListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberAnimatedNavController()

            PokemonMVITheme {
                AnimatedNavHost(navController = navController, startDestination = Navigation.ListScreen.route) {
                    composable(route = Navigation.ListScreen.route, exitTransition = {
                        fadeOut(animationSpec = tween(300))
                    }, popExitTransition = {
                        fadeOut(animationSpec = tween(300))
                    }, enterTransition = {
                        fadeIn(animationSpec = tween(500))
                    }, popEnterTransition = {
                        fadeIn(animationSpec = tween(500))
                    }) {
                        ListScreen(navController = navController)
                    }
                    composable(route = Navigation.DetailScreen.route, enterTransition = {
                        fadeIn(animationSpec = tween(500))
                    }, popEnterTransition = {
                        fadeIn(animationSpec = tween(500))
                    }, exitTransition = {
                        fadeOut(animationSpec = tween(300))
                    }, popExitTransition = {
                        fadeOut(animationSpec = tween(300))
                    }) {
                        val detail = navController.previousBackStackEntry?.savedStateHandle?.get<Pokemon>(NavArgs.Detail.key)
                        detail?.let {
                            DetailScreen(pokemon = it, navController = navController)
                        }
                    }
                }
            }
        }
    }
}