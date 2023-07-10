package com.example.submissionjetpackcompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.submissionjetpackcompose.ui.navigation.Screen
import com.example.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class AnimeListAppTest(){
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionJetpackComposeTheme() {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                AnimeListApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_HomeIsStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.menu_favorite)).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.menu_home)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}