package com.andrew.solokhov.mvvmmovieapp.domain.utils

import java.util.regex.Pattern

object ValidationPasswordPatterns {
    val PASSWORD_AT_LEAST_ONE_DIGIT: Pattern = Pattern.compile("(?=.*[0-9])")
    val PASSWORD_CONTAINS_LOWER_CASE: Pattern = Pattern.compile("(?=.*[a-z])")
    val PASSWORD_CONTAINS_UPPER_CASE: Pattern = Pattern.compile("(?=.*[A-Z])")
    val PASSWORD_SPECIAL_CHARACTER: Pattern = Pattern.compile("(?=.*[@#$%^&+=])")
    val PASSWORD_NO_WHITE_SPACES: Pattern = Pattern.compile("(?=\\S+$)")
    val PASSWORD_LENGTH: Pattern = Pattern.compile("^.{8,}$")
}