package com.example.myapplication.data.network

import com.example.myapplication.model.Pet
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Interfaz Retrofit para comunicar con el Backend Spring Boot
 * Representa los endpoints disponibles en el microservicio
 */
interface BackendApiService {

    // GET: Obtener todas las mascotas
    @GET("api/pets")
    suspend fun getAllPets(): List<Pet>

    // POST: Crear una nueva mascota
    @POST("api/pets")
    suspend fun createPet(@Body pet: Pet): Pet

    // PUT: Actualizar una mascota existente
    @PUT("api/pets/{id}")
    suspend fun updatePet(@Path("id") id: Int, @Body pet: Pet): Pet

    // DELETE: Eliminar una mascota por ID
    @DELETE("api/pets/{id}")
    suspend fun deletePet(@Path("id") id: Int)
}
