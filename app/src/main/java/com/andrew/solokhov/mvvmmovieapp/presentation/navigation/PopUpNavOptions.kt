package com.andrew.solokhov.mvvmmovieapp.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

class PopUpNavOptions(
    navController: NavController,
) {
    val navOptionsBuilder = NavOptions.Builder()
        .setPopUpTo(navController.graph.startDestinationId, true)
}