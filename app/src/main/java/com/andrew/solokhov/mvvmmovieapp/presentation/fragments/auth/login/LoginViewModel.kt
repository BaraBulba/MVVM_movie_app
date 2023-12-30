package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth.login

import androidx.lifecycle.ViewModel
import com.andrew.solokhov.mvvmmovieapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    fun loginWithEmailAndPassword(email: String, password: String) =
        authRepository.loginUser(email, password)

}