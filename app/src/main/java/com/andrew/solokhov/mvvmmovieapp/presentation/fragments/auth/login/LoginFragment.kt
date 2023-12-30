package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.data.utils.ResponseWrapper
import com.andrew.solokhov.mvvmmovieapp.databinding.FragmentLoginBinding
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.removeKeyboard
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.showToastMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null

    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        _binding?.run {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnLogin.setOnClickListener {
                loginUser()
            }
            tvForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_passwordResetFragment)
            }

        }
    }

    private fun FragmentLoginBinding.loginUser() {
        with(requireView()){
            removeKeyboard()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val email = emailTextInput.text.toString()
                val password = passwordTextInput.text.toString()
                viewModel.loginWithEmailAndPassword(email, password).collectLatest { response ->
                    when (response) {
                        is ResponseWrapper.Error -> {
                            progressBar.isVisible = false
                            showToastMessage(response.message ?: getString(R.string.something_went_wrong_try_again))
                        }

                        is ResponseWrapper.Loading -> progressBar.isVisible = true
                        is ResponseWrapper.Success -> {
                            progressBar.isVisible = false
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    }
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}