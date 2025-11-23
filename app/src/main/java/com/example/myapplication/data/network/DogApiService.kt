package com.example.myapplication.data.network

import retrofit2.http.GET

data class DogResponse(
    val message: String,
    val status: String
)

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): DogResponse
}
