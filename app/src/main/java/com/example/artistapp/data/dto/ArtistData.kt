package com.example.artistapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArtistData (
    val strArtist: String? = "",
    val strStyle: String? = "",
    val strArtistThumb: String? = "",
)