package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        _binding?.run {
            btnBack.setOnClickListener{
                findNavController().popBackStack()
            }
            btnSignUp.setOnClickListener {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}