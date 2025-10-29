package com.example.artistapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AlbumResponse (
    val album: List<Album>?
)

