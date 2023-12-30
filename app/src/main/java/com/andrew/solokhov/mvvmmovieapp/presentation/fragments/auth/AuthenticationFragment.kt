package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.text.set
import androidx.navigation.fragment.findNavController
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.databinding.FragmentAuthenticationBinding
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.NewClickableSpan
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.showToastMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {

    private var _binding: FragmentAuthenticationBinding? = null
    private var onSpanTextClick: NewClickableSpan? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAuthenticationBinding.bind(view)
        _binding?.run {
            onSpanTextClick = NewClickableSpan {
                findNavController().navigate(R.id.action_authenticationFragment_to_loginFragment)
            }
            changeLoginTextColor()
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


    @Suppress("DEPRECATION")
    private fun FragmentAuthenticationBinding.changeLoginTextColor() {
        val fullText = getString(R.string.already_have_acc)
        val lastWordStart = fullText.lastIndexOf(' ') + 1
        val spannableString = SpannableString(fullText)

        spannableString.setSpan(
            onSpanTextClick,
            lastWordStart,
            fullText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.grey)),
            0,
            lastWordStart,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.light_blue)),
            lastWordStart,
            fullText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        alreadyHaveAccountTv.text = spannableString
        alreadyHaveAccountTv.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onSpanTextClick = null
        _binding = null
    }
}