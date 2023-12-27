package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.databinding.FragmentAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {

    private var _binding: FragmentAuthenticationBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAuthenticationBinding.bind(view)
        _binding?.run {
            changeLoginTextColor()
            alreadyHaveAccountTv.setOnClickListener {
                findNavController().navigate(R.id.action_authenticationFragment_to_loginFragment)
            }
            btnSignUpWithFb.setOnClickListener {
                showToastMessage(getString(R.string.not_yet_implemented))
            }
            btnSignUpWithApple.setOnClickListener {
                showToastMessage(getString(R.string.not_yet_implemented))
            }
            btnSignUpWithGoogle.setOnClickListener {
                showToastMessage(getString(R.string.not_yet_implemented))
            }
            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_authenticationFragment_to_signUpFragment)
            }
        }
    }

    private fun showToastMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun FragmentAuthenticationBinding.changeLoginTextColor() {
        val fullText = "I already have an account? Login"
        val lastWordStart = fullText.lastIndexOf(' ') + 1
        val spannableString = SpannableString(fullText)

        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#92929D")),
            0,
            lastWordStart,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#12CDD9")),
            lastWordStart,
            fullText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        alreadyHaveAccountTv.text = spannableString
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}