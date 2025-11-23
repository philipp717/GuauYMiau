package com.example.myapplication.data

import android.content.Context
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.PetDao
import com.example.myapplication.data.local.UserDao
import com.example.myapplication.data.network.DogApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val userRepository: UserRepository
    val dogApiService: DogApiService
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val database: AppDatabase by lazy {
        AppDatabase.getDatabase(context)
    }

    private val userDao: UserDao by lazy {
        database.userDao()
    }

    private val petDao: PetDao by lazy {
        database.petDao()
    }

    override val userRepository: UserRepository by lazy {
        UserRepository(userDao, petDao)
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override val dogApiService: DogApiService by lazy {
        retrofit.create(DogApiService::class.java)
    }
}
