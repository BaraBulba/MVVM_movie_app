package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth.sign_up

import androidx.annotation.Keep

@Keep
data class SignUpFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val fullName: String = "",
    val fullNameError: String? = null,
    val acceptedTermsAndPolicy: Boolean = false,
    val termsAndPolicyError: String? = null
)
