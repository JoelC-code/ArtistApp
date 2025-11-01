package com.example.artistapp.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artistapp.data.dto.Album
import com.example.artistapp.data.dto.ArtistData
import com.example.artistapp.data.dto.Song
import com.example.artistapp.data.repository.ArtistRepository
import com.example.artistapp.ui.model.AlbumModel
import com.example.artistapp.ui.model.ArtistModel
import com.example.artistapp.ui.model.SongModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.firstOrNull
import kotlin.collections.map
import kotlin.text.format
import kotlin.text.toIntOrNull

@SuppressLint("HardcodedText")
class ArtistViewModel(
    private val artistData: ArtistRepository
): ViewModel() {

    private val _artist = MutableStateFlow<ArtistModel?>(null)
    val artist: StateFlow<ArtistModel?> = _artist

    private val _albums = MutableStateFlow<List<AlbumModel>>(emptyList())
    val albums: StateFlow<List<AlbumModel>> = _albums

    private val _album = MutableStateFlow<AlbumModel?>(null)
    val album: StateFlow<AlbumModel?> = _album

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    fun selectAlbumById(albumId: String) {
        _album.value = _albums.value.find { it.id == albumId }
    }

    private suspend fun fetchSongForAlbum(album: AlbumModel): List<SongModel> {
        val trackResponse = artistData.getTracksByAlbum(album.id)
        val songDto = trackResponse.track ?: emptyList()
        return songDto.map { it.toModel() }
    }

    private fun fetchArtistData(artistName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            try {
                val artistResponse = artistData.getArtist(artistName)
                val artistDto = artistResponse.artists?.firstOrNull()
                val artistModel = artistDto?.toModel()

                val albumResponse = artistData.getAlbumsByArtist(artistName)
                val albumModels = albumResponse.album?.map { it.toModel() } ?: emptyList()

                val albumsWithSong = albumModels.map { album ->
                    val songs = fetchSongForAlbum(album)
                    album.copy(songs = songs)
                }

                artistModel?.albums = albumsWithSong
                _artist.value = artistModel
                _albums.value = albumsWithSong
                println("Mapped albumModels: \n${albumModels.map { it.title }}\n${albumModels.map { it.thumbnailUrl }}")
                println("Data album from: $artistName fetched album:\n$albumResponse")
            } catch (e: Exception) {
                _isError.value = true
                e.printStackTrace()
                println("Error fetched, check the lines with no index")
            } finally {
                _isLoading.value = false
                println("Everything is done!")
            }
        }
    }

    init {
        fetchArtistData("Pink Floyd")
    }

    fun ArtistData.toModel(): ArtistModel {
        return ArtistModel(
            name = this.strArtist ?: "",
            genre = this.strStyle ?: "",
            thumbnailUrl = this.strArtistThumb ?: "",
            albums = emptyList()
        )
    }

    fun Album.toModel(): AlbumModel {
        return AlbumModel(
            id = this.idAlbum ?: "",
            title = this.strAlbum ?: "",
            style = this.strStyle ?: "",
            year = this.intYearReleased ?: "",
            thumbnailUrl = this.strAlbumThumb ?: "",
            description = this.strDescriptionEN ?: "",
            songs = emptyList()
        )
    }

    fun Song.toModel(): SongModel {
        val durationMs = intDuration.toString().toIntOrNull() ?: 0
        val durationMinutes = (durationMs / 1000) / 60
        val durationSeconds = (durationMs / 1000) % 60
        val durationFormatted = "%d:%02d".format(durationMinutes, durationSeconds)
        return SongModel(
            idSong = this.idSong ?: "",
            trackName = this.strTrack ?: "",
            duration = durationFormatted
        )
    }
}