package com.example.artistapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TrackResponse (
    val track: List<Song>?
)