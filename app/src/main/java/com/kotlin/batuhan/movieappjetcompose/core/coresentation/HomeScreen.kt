package com.kotlin.batuhan.movieappjetcompose.core.coresentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlin.batuhan.movieappjetcompose.R
import com.kotlin.batuhan.movieappjetcompose.movieList.presentation.MovieListUiEvent
import com.kotlin.batuhan.movieappjetcompose.movieList.presentation.MovieListViewModel
import com.kotlin.batuhan.movieappjetcompose.movieList.util.Screen
import com.kotlin.batuhan.movieappjetcompose.movieList.presentation.PopularMoviesScreen
import com.kotlin.batuhan.movieappjetcompose.movieList.presentation.UpcomingMoviesScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    val movieListState = movieListViewModel.movieListState.collectAsState().value
    val bottomNavController = rememberNavController()

    // State for TopAppBar title
    val topAppBarTitle = rememberSaveable { mutableStateOf("") }
    val popularTitle = stringResource(R.string.popular_movies)
    val upcomingTitle = stringResource(R.string.upcoming_movies)
    val favoriteTitle = stringResource(R.string.Favorite)
    val settingsTitle = stringResource(R.string.settings)

    // Initialize with popular movies title
    if (topAppBarTitle.value.isEmpty()) {
        topAppBarTitle.value = popularTitle
    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomNavController = bottomNavController,
                onEvent = movieListViewModel::onEvent,
                onTitleChange = { topAppBarTitle.value = it },
                popularTitle = popularTitle,
                upcomingTitle = upcomingTitle,
                favoriteTitle = favoriteTitle,
                settingsTitle = settingsTitle
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = topAppBarTitle.value,
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier.shadow(2.dp),
                colors = topAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface,
                ),
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = bottomNavController, startDestination = Screen.PopularMovieList.rout) {
                composable(Screen.PopularMovieList.rout) {
                    PopularMoviesScreen(
                        navController = navController,
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.UpcomingMovieList.rout) {
                    UpcomingMoviesScreen(
                        navController = navController,
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.Favorites.rout) {
                    // FavoritesScreen()
                }
                composable(Screen.Settings.rout) {
                    // SettingsScreen()
                }
            }
        }
    }
}

@SuppressLint("AutoboxingStateCreation")
@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController,
    onEvent: (MovieListUiEvent) -> Unit,
    onTitleChange: (String) -> Unit,
    popularTitle: String,
    upcomingTitle: String,
    favoriteTitle: String,
    settingsTitle: String
) {
    val items = listOf(
        BottomItem(
            title = R.string.popular,
            icon = Icons.Rounded.Movie
        ),
        BottomItem(
            title = R.string.upcoming,
            icon = Icons.Rounded.Upcoming
        ),
        BottomItem(
            title = R.string.Favorite,
            icon = Icons.Rounded.Favorite
        ),
        BottomItem(
            title = R.string.settings,
            icon = Icons.Rounded.Settings
        )
    )
    val selected = rememberSaveable { mutableStateOf(0) }

    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, bottomItem ->
                val title = stringResource(id = bottomItem.title)
                NavigationBarItem(
                    selected = selected.value == index,
                    onClick = {
                        selected.value = index
                        when (selected.value) {
                            0 -> {
                                onEvent(MovieListUiEvent.Navigate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.PopularMovieList.rout)
                                onTitleChange(title)
                            }
                            1 -> {
                                onEvent(MovieListUiEvent.Navigate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.UpcomingMovieList.rout)
                                onTitleChange(title)
                            }
                            2 -> {
                                onEvent(MovieListUiEvent.Navigate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.Favorites.rout)
                                onTitleChange(title)
                            }
                            3 -> {
                                onEvent(MovieListUiEvent.Navigate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.Settings.rout)
                                onTitleChange(title)
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}

data class BottomItem(
    val title: Int,
    val icon: ImageVector
)
