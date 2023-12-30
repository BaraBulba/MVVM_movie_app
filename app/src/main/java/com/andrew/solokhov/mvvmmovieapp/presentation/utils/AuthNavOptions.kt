package com.andrew.solokhov.mvvmmovieapp.presentation.utils

import androidx.navigation.NavController
import androidx.navigation.NavOptions

class AuthNavOptions(
    navController: NavController,
) {
    val navOptions = NavOptions.Builder()
    .setPopUpTo(navController.graph.startDestinationId, true)
    .build()
}