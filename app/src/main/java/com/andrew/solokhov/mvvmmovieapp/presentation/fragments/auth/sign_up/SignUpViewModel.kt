package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrew.solokhov.mvvmmovieapp.data.utils.ResponseWrapper
import com.andrew.solokhov.mvvmmovieapp.domain.entity.sign_up.SignUpFormState
import com.andrew.solokhov.mvvmmovieapp.domain.repository.AuthRepository
import com.andrew.solokhov.mvvmmovieapp.domain.usecases.sign_up.ValidateEmailUseCase
import com.andrew.solokhov.mvvmmovieapp.domain.usecases.sign_up.ValidateFullNameUseCase
import com.andrew.solokhov.mvvmmovieapp.domain.usecases.sign_up.ValidatePasswordUseCase
import com.andrew.solokhov.mvvmmovieapp.domain.usecases.sign_up.ValidateTermsAndPrivacyPolicyUseCase
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.SignUpFormEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateTermsAndPrivacyPolicyUseCase: ValidateTermsAndPrivacyPolicyUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
) : ViewModel() {

    private var _validationState = MutableStateFlow(SignUpFormState())
    val validationState = _validationState.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private var _signUpState =
        MutableStateFlow<SignUpResult>(SignUpResult.Empty)
    val signUpState = _signUpState.asStateFlow()

    fun signUpNewUser(email: String, password: String, fullName: String) {
        viewModelScope.launch {
            authRepository.registerUser(email, password, fullName).collectLatest { response ->
                when (response) {
                    is ResponseWrapper.Error -> _signUpState.value =
                        SignUpResult.Error(response.message)

                    is ResponseWrapper.Loading -> _signUpState.value =
                        SignUpResult.Loading

                    is ResponseWrapper.Success -> _signUpState.value =
                        SignUpResult.Success(response.data)
                }
            }
        }
    }

    fun onSignUpEvent(event: SignUpFormEvent) {
        val currentState = _validationState.value
        when (event) {
            is SignUpFormEvent.AcceptTermsAndPolicy -> {
                val termsResult =
                    if (event.isAccepted) null else "You must accept the terms and privacy policy."
                _validationState.value = currentState.copy(
                    acceptedTermsAndPolicy = event.isAccepted,
                    termsAndPolicyError = termsResult
                )
            }

            is SignUpFormEvent.EmailChanged -> {
                _validationState.value = currentState.copy(email = event.email)
                validateEmail(event.email)
            }

            is SignUpFormEvent.FullNameChanged -> {
                _validationState.value = currentState.copy(fullName = event.fullName)
                validateFullName(event.fullName)
            }

            is SignUpFormEvent.PasswordChanged -> {
                _validationState.value = currentState.copy(password = event.password)
                validatePassword(event.password)
            }

            SignUpFormEvent.SignUp -> submitData()
        }
    }

    private fun validateEmail(email: String) {
        val result = validateEmailUseCase(email)
        _validationState.update { it.copy(emailError = result.errorMessage) }
    }

    private fun validatePassword(password: String) {
        val result = validatePasswordUseCase(password)
        _validationState.update { it.copy(passwordError = result.errorMessage) }
    }

    private fun validateFullName(fullName: String) {
        val result = validateFullNameUseCase(fullName)
        _validationState.update { it.copy(fullNameError = result.errorMessage) }
    }

    private fun submitData() {
        _validationState.value.apply {
            val emailResult = validateEmailUseCase(email)
            val passwordResult = validatePasswordUseCase(password)
            val fullNameResult = validateFullNameUseCase(fullName)
            val termsAndPolicyResult =
                validateTermsAndPrivacyPolicyUseCase(acceptedTermsAndPolicy)
            val hasError = listOf(
                emailResult,
                passwordResult,
                fullNameResult,
                termsAndPolicyResult
            ).any { !it.successful }

            if (hasError) {
                _validationState.update { state ->
                    state.copy(
                        emailError = if (!emailResult.successful) emailResult.errorMessage else null,
                        passwordError = if (!passwordResult.successful) passwordResult.errorMessage else null,
                        fullNameError = if (!fullNameResult.successful) fullNameResult.errorMessage else null,
                        termsAndPolicyError = if (!termsAndPolicyResult.successful) termsAndPolicyResult.errorMessage else null
                    )
                }
                return
            }
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Success)
            }
        }

    }


    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }

}