package com.kotlin.batuhan.movieappjetcompose.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kotlin.batuhan.movieappjetcompose.movieList.util.Screen
import com.kotlin.batuhan.movieappjetcompose.ui.theme.MovieAppJetComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppJetComposeTheme {

                SetBarColor(color = MaterialTheme.colorScheme.inverseOnSurface)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

           //      val movieListViewModel = hiltViewModel<MovieListViewModel>()
                   val navController = rememberNavController()

                   NavHost(
                       navController = navController,
                       startDestination = Screen.Home.rout,
                   ){
                       composable(Screen.Home.rout){
                             HomeScreen(navController)
                       }
                       composable(Screen.Details.rout + "/{movieId}",
                        arguments = listOf(
                            navArgument(name = "movieId"){ type = NavType.IntType}
                        ) ){backStackEntry ->
                        //    DetailScreen(backStackEntry)
                       }

                       }
                   }
                }
            }
        }
@Suppress("DEPRECATION")
@Composable
private fun SetBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        LaunchedEffect(key1 = color) {
            systemUiController.setSystemBarsColor(color)
        }
    }
    }

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieAppJetComposeTheme {
        Greeting("Android")
    }
}