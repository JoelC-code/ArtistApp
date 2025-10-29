package com.example.artistapp.ui.model

data class ArtistModel (
    val name: String,
    val genre: String,
    val thumbnailUrl: String,
    var albums: List<AlbumModel>
)