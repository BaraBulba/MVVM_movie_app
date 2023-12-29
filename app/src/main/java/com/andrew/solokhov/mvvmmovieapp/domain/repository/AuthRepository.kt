package com.andrew.solokhov.mvvmmovieapp.domain.repository

import com.andrew.solokhov.mvvmmovieapp.data.utils.ResponseWrapper
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun registerUser(email: String, password: String): Flow<ResponseWrapper<Boolean>>
}