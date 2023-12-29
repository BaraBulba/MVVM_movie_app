package com.andrew.solokhov.mvvmmovieapp.domain.usecases.sign_up

import com.andrew.solokhov.mvvmmovieapp.domain.utils.ValidationPasswordPatterns.PASSWORD_CONTAINS_LOWER_CASE
import com.andrew.solokhov.mvvmmovieapp.domain.utils.ValidationPasswordPatterns.PASSWORD_CONTAINS_UPPER_CASE
import com.andrew.solokhov.mvvmmovieapp.domain.utils.ValidationPasswordPatterns.PASSWORD_LENGTH
import com.andrew.solokhov.mvvmmovieapp.domain.utils.ValidationPasswordPatterns.PASSWORD_NO_WHITE_SPACES
import com.andrew.solokhov.mvvmmovieapp.domain.utils.ValidationPasswordPatterns.PASSWORD_SPECIAL_CHARACTER
import com.andrew.solokhov.mvvmmovieapp.domain.entity.sign_up.ValidationResult
import com.andrew.solokhov.mvvmmovieapp.domain.utils.ValidationPasswordPatterns.PASSWORD_AT_LEAST_ONE_DIGIT
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): ValidationResult {
        if (!PASSWORD_LENGTH.matcher(password).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password should contain at least 8 characters!"
            )
        }

        if (!PASSWORD_CONTAINS_LOWER_CASE.matcher(password).find()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password should contain at least 1 lower case character!"
            )
        }

        if (!PASSWORD_CONTAINS_UPPER_CASE.matcher(password).find()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password should contain at least 1 upper case character!"
            )
        }

        if (!PASSWORD_SPECIAL_CHARACTER.matcher(password).find()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password should contain at least 1 special character!"
            )
        }

        if (!PASSWORD_NO_WHITE_SPACES.matcher(password).find()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password should not contain white spaces!"
            )
        }

        if (!PASSWORD_AT_LEAST_ONE_DIGIT.matcher(password).find()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password should contain at least 1 digit!"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}