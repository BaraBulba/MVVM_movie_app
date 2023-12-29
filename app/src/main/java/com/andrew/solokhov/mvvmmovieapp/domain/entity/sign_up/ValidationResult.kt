package com.andrew.solokhov.mvvmmovieapp.domain.entity.sign_up

import androidx.annotation.Keep

@Keep
data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
