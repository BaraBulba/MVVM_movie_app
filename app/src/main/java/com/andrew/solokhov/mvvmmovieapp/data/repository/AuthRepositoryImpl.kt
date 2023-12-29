package com.andrew.solokhov.mvvmmovieapp.data.repository

import com.andrew.solokhov.mvvmmovieapp.data.utils.ResponseWrapper
import com.andrew.solokhov.mvvmmovieapp.di.provide.IoDispatcher
import com.andrew.solokhov.mvvmmovieapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @IoDispatcher private val io: CoroutineDispatcher,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseCrashlytics: FirebaseCrashlytics,
) : AuthRepository {
    override fun registerUser(email: String, password: String): Flow<ResponseWrapper<Boolean>> {
        return flow {
            emit(ResponseWrapper.Loading())
            val response = firebaseAuth.createUserWithEmailAndPassword(
                email, password
            )
            response.await()
            if (response.isSuccessful) {
                emit(ResponseWrapper.Success(true))
            } else emit(ResponseWrapper.Error(response.exception?.message))
        }.flowOn(io).catch { throwable ->
            firebaseCrashlytics.recordException(throwable)
            emit(ResponseWrapper.Error(throwable.localizedMessage))
        }
    }

}