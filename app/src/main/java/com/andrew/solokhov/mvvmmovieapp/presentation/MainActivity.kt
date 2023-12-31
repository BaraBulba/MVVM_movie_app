package com.andrew.solokhov.mvvmmovieapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.databinding.ActivityMainBinding
import com.andrew.solokhov.mvvmmovieapp.presentation.navigation.PopUpNavOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var binding: ActivityMainBinding

    private val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container_view_tag)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    private var isFirstCreation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigation = currentNavigationFragment?.findNavController()
        navigation?.addOnDestinationChangedListener { _, destination, _ ->
            showBottomNavBar(destination.id, navigation)
        }
        binding.bottomNavBar.setItemSelected(R.id.homeFragment)
    }

    override fun onStart() {
        super.onStart()
        if (isFirstCreation) {
            checkIfUserAuthenticated()
            isFirstCreation = false
        }
    }

    private fun showBottomNavBar(destination: Int, navController: NavController?) {
        with(binding.bottomNavBar) {
            when (destination) {
                R.id.authenticationFragment -> {
                    visibility = View.GONE
                }

                R.id.loginFragment -> {
                    visibility = View.GONE
                }

                R.id.signUpFragment -> {
                    visibility = View.GONE
                }

                R.id.privacyPolicyFragment -> {
                    visibility = View.GONE
                }

                R.id.passwordResetFragment -> {
                    visibility = View.GONE
                }

                else -> {
                    visibility = View.VISIBLE
                }
            }
        }
        binding.bottomNavBar.setOnItemSelectedListener {
            when (it) {
                R.id.homeFragment -> {
                    navController?.navigate(R.id.homeFragment)
                }
                R.id.searchFragment -> {
                    navController?.navigate(R.id.searchFragment)
                }
                R.id.profileFragment -> {
                    navController?.navigate(R.id.profileFragment)
                }
            }
        }
    }

    private fun checkIfUserAuthenticated() {
        val navController = findNavController(R.id.fragment_container_view_tag)
        val navOptions = PopUpNavOptions(navController).navOptions
        firebaseAuth.currentUser?.let {
            navController.navigate(R.id.homeFragment, null, navOptions)
        } ?: navController.navigate(R.id.authenticationFragment, null, navOptions)
    }
}