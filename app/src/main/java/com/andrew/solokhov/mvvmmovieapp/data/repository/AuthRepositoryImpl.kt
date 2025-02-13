package com.andrew.solokhov.mvvmmovieapp.data.repository

import com.andrew.solokhov.mvvmmovieapp.data.utils.ResponseWrapper
import com.andrew.solokhov.mvvmmovieapp.di.provide.IoDispatcher
import com.andrew.solokhov.mvvmmovieapp.domain.repository.AuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @IoDispatcher private val io: CoroutineDispatcher,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseCrashlytics: FirebaseCrashlytics,
) : AuthRepository {

    override fun registerUser(
        email: String,
        password: String,
        fullName: String
    ): Flow<ResponseWrapper<Boolean>> {
        return flow {
            emit(ResponseWrapper.Loading(true))
            val response = firebaseAuth.createUserWithEmailAndPassword(
                email, password
            )
            response.await()
            if (response.isSuccessful) {
                updateUserProfileName(fullName)
                emit(ResponseWrapper.Loading(false))
                emit(ResponseWrapper.Success(true))
            } else {
                emit(ResponseWrapper.Loading(false))
                emit(ResponseWrapper.Error(response.exception?.message))
            }
        }.flowOn(io).catch { throwable ->
            firebaseCrashlytics.recordException(throwable)
            emit(ResponseWrapper.Loading(false))
            emit(ResponseWrapper.Error(throwable.localizedMessage))
        }
    }

    override fun loginUser(email: String, password: String): Flow<ResponseWrapper<Boolean>> {
        return performFirebaseAuthOperation(
            operation = {
                firebaseAuth.signInWithEmailAndPassword(email, password)
            },
            firebaseCrashlytics = firebaseCrashlytics
        )
    }

    override suspend fun updateUserProfileName(fullName: String) {
        firebaseAuth.currentUser?.let { user ->
            val profileUpdate = UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build()
            withContext(io) {
                try {
                    user.updateProfile(profileUpdate).await()
                } catch (e: Exception) {
                    firebaseCrashlytics.recordException(e)
                }
            }
        }

    }

    override fun resetUserPasswordWithEmail(email: String): Flow<ResponseWrapper<Boolean>> {
        return performFirebaseAuthOperation(
            operation = {
                firebaseAuth.sendPasswordResetEmail(email)
            },
            firebaseCrashlytics = firebaseCrashlytics
        )
    }

    override suspend fun userSignOut() {
        withContext(io){
            firebaseAuth.signOut()
        }
    }

    private fun <T> performFirebaseAuthOperation(
        operation: suspend () -> Task<T>,
        firebaseCrashlytics: FirebaseCrashlytics
    ): Flow<ResponseWrapper<Boolean>> {
        return flow {
            emit(ResponseWrapper.Loading())
            val response = operation()
            response.await()
            if (response.isSuccessful) {
                emit(ResponseWrapper.Success(true))
            } else {
                emit(ResponseWrapper.Error(response.exception?.message))
            }
        }.flowOn(io).catch { throwable ->
            firebaseCrashlytics.recordException(throwable)
            emit(ResponseWrapper.Error(throwable.localizedMessage))
        }
    }

}