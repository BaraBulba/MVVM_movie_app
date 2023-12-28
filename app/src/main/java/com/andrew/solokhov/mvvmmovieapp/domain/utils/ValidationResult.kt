package com.andrew.solokhov.mvvmmovieapp.domain.utils

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
