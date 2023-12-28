package com.andrew.solokhov.mvvmmovieapp.domain.usecases.sign_up

import android.util.Patterns
import com.andrew.solokhov.mvvmmovieapp.domain.utils.ValidationResult
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email field can't be blank!"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Not a valid email!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}