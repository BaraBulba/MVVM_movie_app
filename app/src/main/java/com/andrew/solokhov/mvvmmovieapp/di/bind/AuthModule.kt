package com.andrew.solokhov.mvvmmovieapp.di.bind

import com.andrew.solokhov.mvvmmovieapp.data.repository.AuthRepositoryImpl
import com.andrew.solokhov.mvvmmovieapp.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@Suppress("FunctionName")
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthRepoImpl_to_AuthRepo(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}