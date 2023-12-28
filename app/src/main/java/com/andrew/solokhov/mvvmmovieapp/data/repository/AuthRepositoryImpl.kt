package com.andrew.solokhov.mvvmmovieapp.data.repository

import com.andrew.solokhov.mvvmmovieapp.domain.repository.AuthRepository

class AuthRepositoryImpl: AuthRepository {
    override suspend fun registerUser(email: String, password: String) {
    }
}