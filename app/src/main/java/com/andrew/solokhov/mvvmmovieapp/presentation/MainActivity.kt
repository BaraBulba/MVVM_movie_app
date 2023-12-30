package com.andrew.solokhov.mvvmmovieapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.AuthNavOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private var isFirstCreation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        if (isFirstCreation) {
            checkIfUserAuthenticated()
            isFirstCreation = false
        }
    }

    private fun checkIfUserAuthenticated() {
        val navController = findNavController(R.id.fragment_container_view_tag)
        val navOptions = AuthNavOptions(navController).navOptions
        firebaseAuth.currentUser?.let {
            navController.navigate(R.id.homeFragment, null, navOptions)
        } ?: navController.navigate(R.id.authenticationFragment, null, navOptions)
    }
}