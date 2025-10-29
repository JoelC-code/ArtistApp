package com.example.artistapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArtistResponse (
    val artists: List<ArtistData>?
)