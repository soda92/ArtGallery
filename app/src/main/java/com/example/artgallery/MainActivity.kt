package com.example.artgallery

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artgallery.ui.theme.ArtGalleryTheme
import kotlinx.parcelize.Parcelize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtGalleryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ArtGallery()
                }
            }
        }
    }
}

@Parcelize
data class ArtContainer(val image: Int, val title: String, val coser: String = "Unknown") :
    Parcelable {
    override fun toString(): String {
        return "$title - $coser"
    }
}

val ArtData: List<ArtContainer> = listOf(
    ArtContainer(image = R.drawable.little_sister_aliga, title = "Little Sister", coser = "Aliga"),
    ArtContainer(image = R.drawable.maid_unknown, title = "Maid"),
    ArtContainer(image = R.drawable.outdoor_kenko, title = "Outdoor", coser = "Kenko")
)

@Preview
@Composable
fun ArtGallery(modifier: Modifier = Modifier) {
    var artIndex by rememberSaveable { mutableStateOf(0) }
    var artData by rememberSaveable { mutableStateOf(ArtData[artIndex]) }

    fun nextArt(): ArtContainer {
        artIndex = (artIndex + 1) % (ArtData.size)
        return ArtData[artIndex]
    }

    fun prevArt(): ArtContainer {
        artIndex = (artIndex + ArtData.size - 1) % (ArtData.size)
        return ArtData[artIndex]
    }
    ArtPiece(
        onNext = { artData = nextArt() },
        onPrev = { artData = prevArt() },
        artData = artData,
        modifier = modifier
    )
}

@Composable
fun ArtPiece(
    onNext: () -> Unit,
    onPrev: () -> Unit,
    artData: ArtContainer,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(0.8F)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = artData.image),
                contentDescription = artData.toString(),
                modifier = Modifier
                    .padding(16.dp)
                    .border(width = 3.dp, color = Color.DarkGray)
                    .shadow(elevation = 16.dp, shape = RectangleShape)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = artData.title, fontSize = 24.sp, fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = artData.coser, fontSize = 16.sp,
                textAlign = TextAlign.Justify
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        ) {
            Button(onClick = onPrev) {
                Text(text = "Prev")
            }
            Button(onClick = onNext) {
                Text(text = "Next")
            }
        }
    }
}
