package com.example.artistapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AlbumDetailResponse (
    val album: List<Album>
)