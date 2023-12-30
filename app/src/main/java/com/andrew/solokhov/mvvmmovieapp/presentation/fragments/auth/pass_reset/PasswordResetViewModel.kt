package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth.pass_reset

import androidx.lifecycle.ViewModel
import com.andrew.solokhov.mvvmmovieapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordResetViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    fun resetPasswordWithEmail(email: String) =
        authRepository.resetUserPasswordWithEmail(email)
}