package com.example.myapplication.model

data class Pet(
    val id: Int = 0,
    val name: String,
    val type: String,
    val ownerEmail: String
)

data class User(
    val email: String,
    val password: String
)
