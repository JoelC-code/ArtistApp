package com.example.artistapp.ui.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.artistapp.ui.view.AlbumCardDetail
import com.example.artistapp.ui.viewmodel.ArtistViewModel
import com.example.artistapp.ui.view.ArtistView

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyTopNavBar(
    navController: NavController,
    viewModel: ArtistViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val artist by viewModel.artist.collectAsState()
    val currentAlbum by viewModel.album.collectAsState()
    val errorStatus by viewModel.isError.collectAsState()

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = when {
                    currentRoute?.startsWith("album_detail") == true -> currentAlbum?.title ?: ""
                    errorStatus -> "Error"
                    else -> artist?.name ?: "Loading"
                },
                color =
                    if(!errorStatus) {
                        Color(0xFFADFFB3)
                    } else {
                        Color.Red
                    },
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (currentRoute?.startsWith("album_detail") == true) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFFADFFB3)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1E1D1E)
        )
    )
}

@Composable
fun ArtistAppScreen(viewModel: ArtistViewModel) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            MyTopNavBar(
                navController = navController,
                viewModel = viewModel
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "artist",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("artist") {
                ArtistView(
                    viewModel = viewModel,
                    onAlbumClick = { album ->
                        navController.navigate("album_detail/${album.id}")
                    }
                )
            }

            composable(
                //Defines the route of the page that's accessed
                route = "album_detail/{albumId}",
                //defines the argument that albumId will be a string type
                arguments = listOf(navArgument("albumId") { type = NavType.StringType })
            ) { backStackEntry ->
                //albumId is here to retrieve from the argument above
                val albumId = backStackEntry.arguments?.getString("albumId")
                //using the fetch in viewModel, we use it here to fetch a singular album data
                if (albumId != null) {
                    viewModel.selectAlbumById(albumId)
                }
                //take album (single) from viewmodel and put it here as albumSingular
                val albumSingular by viewModel.album.collectAsState()

                AlbumCardDetail(album = albumSingular)
            }
        }
    }
}