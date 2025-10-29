package com.example.artistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.artistapp.data.container.ArtistContainer
import com.example.artistapp.data.repository.ArtistRepository
import com.example.artistapp.ui.route.ArtistAppScreen
import com.example.artistapp.ui.theme.ArtistAppTheme
import com.example.artistapp.ui.viewmodel.ArtistViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = ArtistRepository(ArtistContainer.api)
        val viewModel = ArtistViewModel(repository)
        setContent {
            ArtistAppTheme {
                ArtistAppScreen(viewModel)
            }
        }
    }
}
