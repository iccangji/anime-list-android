package com.example.submissionjetpackcompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchAnimeTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionJetpackComposeTheme() {
                AnimeListApp()
            }
        }
    }

    @Test
    fun search_anime(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.placeholder_search_anime))
            .performTextInput("rock")
        composeTestRule.onNodeWithText(
            "Bocchi the Rock!").assertIsDisplayed()
    }

    @Test
    fun search_anime_incorrect(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.placeholder_search_anime))
            .performTextInput("Naruto")
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.no_data)).assertIsDisplayed()
    }
}