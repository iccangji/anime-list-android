package com.example.submissionjetpackcompose.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object DetailAnime : Screen("home/{animeId}/{favorite}"){
        fun createRoute(animeId: Int, favorite: Boolean) = "home/$animeId/$favorite"
    }
}