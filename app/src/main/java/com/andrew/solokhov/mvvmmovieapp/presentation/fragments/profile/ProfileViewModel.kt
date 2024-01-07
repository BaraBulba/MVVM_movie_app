package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrew.solokhov.mvvmmovieapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    fun signOut() = viewModelScope.launch { authRepository.userSignOut() }
}