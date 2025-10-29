package com.example.artistapp.data.service

import com.example.artistapp.data.dto.AlbumDetailResponse
import com.example.artistapp.data.dto.AlbumResponse
import com.example.artistapp.data.dto.ArtistResponse
import com.example.artistapp.data.dto.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistService {
    @GET("search.php")
    suspend fun getArtist(
        @Query("s") artistName: String
    ): ArtistResponse

    @GET("searchalbum.php")
    suspend fun getAllArtistAlbums(
        @Query("s") artistName: String
    ): AlbumResponse

    @GET("album.php")
    suspend fun getAlbumDetails(
        @Query("m") albumId: String
    ): AlbumDetailResponse

    @GET("track.php")
    suspend fun getTracksByAlbum(
        @Query("m") albumId: String
    ): TrackResponse
}