package com.andrew.solokhov.mvvmmovieapp.domain.repository

import com.andrew.solokhov.mvvmmovieapp.data.utils.ResponseWrapper
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun registerUser(email: String, password: String, fullName: String): Flow<ResponseWrapper<Boolean>>
    fun loginUser(email: String, password: String): Flow<ResponseWrapper<Boolean>>
    suspend fun updateUserProfileName(fullName: String)
    fun resetUserPasswordWithEmail(email: String): Flow<ResponseWrapper<Boolean>>
    suspend fun userSignOut()
}