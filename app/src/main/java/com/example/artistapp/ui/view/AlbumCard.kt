package com.example.artistapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artistapp.ui.model.AlbumModel
import com.example.artistapp.R

@Composable
fun AlbumCard(
    album: AlbumModel?,
    onClick: (AlbumModel?) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(1f)
            .clickable{ onClick(album) },
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF191F1F))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(album!!.thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = album.title,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .padding(start = 9.dp, bottom = 9.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = album.title,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFFADFFB3),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Text(
                    text = "${album.year} • ${album.style}",
                    fontSize = 9.sp,
                    color = Color(0xFFADFFB3),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AlbumCardPreview() {
    val dummyData = AlbumModel(
        id = "1",
        title = "Divide",
        style = "Pop",
        year = "2017",
        thumbnailUrl = "https://r2.theaudiodb.com/images/media/artist/thumb/uhsmur1713812514.jpg",
        description = "Divide is the third studio album by English singer-songwriter Ed Sheeran.",
    )
    AlbumCard(
        album = dummyData,
        onClick = {}
    )
}