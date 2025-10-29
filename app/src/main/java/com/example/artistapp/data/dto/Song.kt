package com.example.artistapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val idSong: String? = "",
    val strTrack: String? = "",
    val intDuration: Int? = 0
)