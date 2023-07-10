package com.example.submissionjetpackcompose.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.submissionjetpackcompose.ui.components.TopNavBar
import com.example.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme

@Composable
fun AboutScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                modifier = modifier
                    .shadow(16.dp)
            ) {
                TopNavBar(
                    modifier = modifier,
                    navigateBack = navigateBack,
                    title = "About"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://scontent-scl2-1.cdninstagram.com/v/t51.2885-19/343304252_1317291999134599_3823591860776195800_n.jpg?stp=dst-jpg_s320x320&_nc_ht=scontent-scl2-1.cdninstagram.com&_nc_cat=107&_nc_ohc=qttYUzEqjQcAX_LHqgh&edm=AOQ1c0wBAAAA&ccb=7-5&oh=00_AfBCBMYTyKkfMiJPfHkht_jOANHPH4GQY992lfpdZyqY5g&oe=6471CBAF&_nc_sid=f70a57&dl=1",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(paddingValues)
                    .width(160.dp)
                    .height(160.dp)
                    .clip(CircleShape)
                    .shadow(8.dp)
            )
            Text(
                text = "Muh. Nur Iksan",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(top = 16.dp),
            )
            Text(
                text = "a173dkx4494@bangkit.academy",
                modifier = modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutPreview(){
    SubmissionJetpackComposeTheme {
        AboutScreen(navigateBack = {})
    }
}