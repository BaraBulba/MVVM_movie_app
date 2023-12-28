package com.andrew.solokhov.mvvmmovieapp.domain.usecases.sign_up

import com.andrew.solokhov.mvvmmovieapp.domain.utils.ValidationResult
import javax.inject.Inject

class ValidateTermsAndPrivacyPolicyUseCase @Inject constructor() {
    operator fun invoke(acceptedTermsAndPolicy: Boolean): ValidationResult {
        if (!acceptedTermsAndPolicy) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please accept the Terms of use and Privacy Policy!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}