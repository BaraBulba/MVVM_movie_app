package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth.sign_up

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private var _binding: FragmentSignUpBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        _binding?.run {
            changeTextColorPartly()
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    //Created two ForeGroundColorSpan, so it applies color change to both strings.
    private fun FragmentSignUpBinding.changeTextColorPartly() {
        val fullText = getString(R.string.terms_of_use)
        val ssBuilder = SpannableStringBuilder(fullText)
        val color = ContextCompat.getColor(requireContext(), R.color.light_blue)
        val colorSpan = ForegroundColorSpan(color)
        val colorSpan2 = ForegroundColorSpan(color)
        ssBuilder.setSpan(colorSpan, 38, 52, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssBuilder.setSpan(colorSpan2, 15, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvTermsAndServicesPlusPolicy.text = ssBuilder
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}