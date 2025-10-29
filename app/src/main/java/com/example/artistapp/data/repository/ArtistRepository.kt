package com.example.artistapp.data.repository

import com.example.artistapp.data.dto.*
import com.example.artistapp.data.service.ArtistService

class ArtistRepository(private val service: ArtistService) {
    suspend fun getArtist(artistName: String): ArtistResponse {
        return service.getArtist(artistName)
    }

    suspend fun getAlbumsByArtist(artistName: String): AlbumResponse {
        return service.getAllArtistAlbums(artistName)
    }

    suspend fun getTracksByAlbum(albumId: String): TrackResponse {
        return service.getTracksByAlbum(albumId)
    }
}