package com.example.submissionjetpackcompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.submissionjetpackcompose.data.entity.AnimeEntity
import com.example.submissionjetpackcompose.data.remote.entity.GenresItem
import com.example.submissionjetpackcompose.model.DetailAnime
import com.example.submissionjetpackcompose.ui.screen.DetailContent
import com.example.submissionjetpackcompose.ui.screen.FavoriteContent
import com.example.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddToFavoriteTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val dummyDetailItem = DetailAnime(
        id = 47917,
        title = "Bocchi the Rock!",
        japan_title = "ぼっち・ざ・ろっく！",
        image = "https://cdn.myanimelist.net/images/anime/1448/127956l.jpg",
        synopsis = "Yearning to make friends and perform live with a band, lonely and socially anxious Hitori \"Bocchi\" Gotou devotes her time to playing the guitar.",
        year = 2022,
        genre = listOf(
            GenresItem(
                name =  "Comedy",
                malId = 4,
                type= "anime",
                url = "https://myanimelist.net/anime/genre/4/Comedy"
            )
        )
    )

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun addFavorite(){
        composeTestRule.setContent {
            SubmissionJetpackComposeTheme {
                DetailContent(
                    detailItem = dummyDetailItem,
                    favorite = false,
                    navigateToFavorite = {},
                    navigateBack = {}
                )
            }
        }

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.add_favorite))
            .assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.add_favorite))
            .assertIsEnabled()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.add_favorite))
            .performClick()

        GlobalScope.launch {
            delay(2000)
            composeTestRule.onNodeWithText(dummyDetailItem.title)
            .assertExists()
        }


    }

    @Test
    fun hasBeenFavorite(){
        composeTestRule.setContent {
            SubmissionJetpackComposeTheme {
                DetailContent(
                    detailItem = dummyDetailItem,
                    favorite = true,
                    navigateBack = { },
                    navigateToFavorite = {}
                )
            }
        }

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.added_favorite))
            .assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.added_favorite))
            .assertIsNotEnabled()
    }
}