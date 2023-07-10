package com.example.submissionjetpackcompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.submissionjetpackcompose.ui.screen.DetailContent
import com.example.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AboutPageTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionJetpackComposeTheme {
                AnimeListApp()
            }
        }
    }

    @Test
    fun aboutPageOpenFromHome(){
        composeTestRule.onNodeWithContentDescription("about_page")
            .performClick()
        composeTestRule.onNodeWithText("Muh. Nur Iksan")
            .assertIsDisplayed()
    }

    @Test
    fun aboutPageOpenFromFavorite(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.menu_favorite))
            .assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.menu_favorite))
            .performClick()
        composeTestRule.onNodeWithContentDescription("about_page")
            .assertDoesNotExist()
    }
}