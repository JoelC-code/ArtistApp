package com.example.artistapp.data.container

import com.example.artistapp.data.service.ArtistService
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

object ArtistContainer {
    private const val BASE_URL = "https://www.theaudiodb.com/api/v1/json/123/"

    private val jsonFile = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(jsonFile.asConverterFactory("application/json".toMediaType()))
        .build()

    val api: ArtistService = retrofit.create(ArtistService::class.java)
}