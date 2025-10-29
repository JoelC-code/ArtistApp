package com.example.artistapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artistapp.R
import com.example.artistapp.ui.model.AlbumModel
import com.example.artistapp.ui.viewmodel.ArtistViewModel

@Composable
fun ArtistView(
    viewModel: ArtistViewModel = viewModel(),
    onAlbumClick: (AlbumModel) -> Unit
) {
    val artist by viewModel.artist.collectAsState()
    val albums by viewModel.albums.collectAsState()
    val loadingStatus by viewModel.isLoading.collectAsState()
    val errorStatus by viewModel.isError.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF292729)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .statusBarsPadding()
        ) {
            //Pink_Floyd thumbnail
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(artist?.thumbnailUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = artist?.name,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "${artist?.name}",
                        fontSize = 30.sp,
                        color = Color(0xFF262626)
                    )
                    Text(
                        text = "${artist?.genre}",
                        fontSize = 12.sp,
                        color = Color(0xFF262626)
                    )
                }
            }
            if(!loadingStatus && !errorStatus) {
                Text(
                    text = "Albums",
                    fontSize = 18.sp,
                    color = Color(0xFFADFFB3),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                )
            }
            //Grids for Lazy
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f),
            ) {
                items(albums) { album ->
                    AlbumCard(
                        album = album,
                        onClick = { onAlbumClick(album) }
                    )
                }
            }
        }
        //For loading
        if (loadingStatus) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color(0xFFA7FF96))
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        if(errorStatus) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Error, Something went wrong. check your internet", color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun AlbumCardDetail(
    album: AlbumModel?,
) {
    println("List of this album (${album?.title}) is: ${album?.songs}")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF292729))
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .statusBarsPadding()
        ) {
            item {
                //Cards on top
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF191F1F),
                            shape = RoundedCornerShape(12.dp))
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
                            fontSize = 30.sp,
                            color = Color(0xFFADFFB3),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Text(
                            text = "${album.year} • ${album.style}",
                            fontSize = 12.sp,
                            color = Color(0xFFADFFB3),
                            modifier = Modifier.padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            //don't follow the suggestion or the desc will break
                            text = "${album.description}",
                            fontSize = 15.sp,
                            color = Color(0xFFADFFB3),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
            item {
                Text(
                    text = "Tracks",
                    fontSize = 18.sp,
                    color = Color(0xFFADFFB3),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                )
            }

            album?.songs?.let { songs ->
                itemsIndexed(songs) { index, song ->
                    SongRow(
                        index = index + 1,
                        title = song.trackName,
                        duration = song.duration
                    )
                }
            }
        }
    }
}

@Composable
fun SongRow(
    index: Int,
    title: String,
    duration: String
) {
    Column(
        modifier = Modifier
            .padding(bottom = 5.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //for the number and title
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = Color(0xFF6C5F3D),
                            shape = RoundedCornerShape(12.dp)
                            )
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text(
                        text = index.toString(),
                        color = Color(0xFFD7B97A),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    color = Color(0xFFDEDEDE),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = duration,
                color = Color(0xFFDEDEDE),
                fontSize = 16.sp
            )
        }
        HorizontalDivider()
    }
}