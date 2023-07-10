package com.example.submissionjetpackcompose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.submissionjetpackcompose.ui.components.BottomBar
import com.example.submissionjetpackcompose.ui.navigation.Screen
import com.example.submissionjetpackcompose.ui.screen.AboutScreen
import com.example.submissionjetpackcompose.ui.screen.DetailScreen
import com.example.submissionjetpackcompose.ui.screen.FavoriteScreen
import com.example.submissionjetpackcompose.ui.screen.HomeScreen
import com.example.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme

@Composable
fun AnimeListApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if(currentRoute == Screen.Home.route
                || currentRoute == Screen.Favorite.route) {
                BottomBar(
                    navController
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { id, favorite ->
                        navController.navigate(Screen.DetailAnime.createRoute(id, favorite))
                    },
                    navigateToAbout = {
                        navController.navigate(Screen.About.route)
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { id, favorite ->
                        navController.navigate(Screen.DetailAnime.createRoute(id, favorite))
                    }
                )
            }

            composable(
                route = Screen.DetailAnime.route,
                arguments = listOf(
                    navArgument("animeId"){type = NavType.IntType},
                    navArgument("favorite"){type = NavType.BoolType},
                )
            ){
                val id = it.arguments?.getInt("animeId") ?: -1
                val favorite = it.arguments?.getBoolean("favorite") ?: false
                DetailScreen(
                    anime = id,
                    favorite = favorite,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToFavorite = {
                        navController.popBackStack()
                        navController.navigate(Screen.Favorite.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Screen.About.route) {
                AboutScreen(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun AnimeListPreview(){
    SubmissionJetpackComposeTheme {
        AnimeListApp()
    }
}