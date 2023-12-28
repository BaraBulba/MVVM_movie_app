package com.andrew.solokhov.mvvmmovieapp.domain.usecases.sign_up

import com.andrew.solokhov.mvvmmovieapp.domain.utils.ValidationResult
import javax.inject.Inject

class ValidateFullNameUseCase @Inject constructor() {
    operator fun invoke(fullName: String): ValidationResult {
        if (fullName.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "This field should not be empty"
            )
        }
        if (fullName.first().isTitleCase()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name should start with title letter!"
            )
        }
        val containsDigits =
            fullName.any { it.isDigit() }
        if (containsDigits){
            return ValidationResult(
                successful = false,
                errorMessage = "The name should not contain digits!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}