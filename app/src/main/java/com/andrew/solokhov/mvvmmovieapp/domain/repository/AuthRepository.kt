package com.andrew.solokhov.mvvmmovieapp.domain.repository

interface AuthRepository {
    suspend fun registerUser(email: String, password: String)
}