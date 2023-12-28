package com.andrew.solokhov.mvvmmovieapp.domain.usecases.sign_up

import com.andrew.solokhov.mvvmmovieapp.domain.utils.ValidationConstants.PASSWORD_LENGTH
import com.andrew.solokhov.mvvmmovieapp.domain.utils.ValidationResult
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): ValidationResult {
        if (password.length < PASSWORD_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password should contain at least 8 characters!"
            )
        }
        val containsLettersAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigits){
            return ValidationResult(
                successful = false,
                errorMessage = "The password should contain at least 1 letter or digit!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}