package com.example.artistapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Album (
    val idAlbum: String? = "",
    val strStyle: String? = "",
    val strAlbum: String? = "",
    val strAlbumThumb: String? = "",
    val intYearReleased: String? = "",
    val strDescriptionEN: String? = "",
)