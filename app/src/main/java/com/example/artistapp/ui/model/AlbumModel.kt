package com.example.artistapp.ui.model

data class AlbumModel (
    val id: String,
    val title: String,
    val style: String,
    val year: String,
    val thumbnailUrl: String,
    val description: String,
    var songs: List<SongModel> = emptyList()
)