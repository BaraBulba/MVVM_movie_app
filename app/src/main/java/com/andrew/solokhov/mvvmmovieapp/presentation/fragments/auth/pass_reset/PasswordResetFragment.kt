package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth.pass_reset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.data.utils.ResponseWrapper
import com.andrew.solokhov.mvvmmovieapp.databinding.FragmentPasswordResetBinding
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.removeKeyboard
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.showToastMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasswordResetFragment : Fragment(R.layout.fragment_password_reset) {

    private var _binding: FragmentPasswordResetBinding? = null

    private val viewModel by viewModels<PasswordResetViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPasswordResetBinding.bind(view)
        _binding?.run {
            btnNext.setOnClickListener {
                with(requireView()) {
                    removeKeyboard()
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.resetPasswordWithEmail(emailTextInput.text.toString())
                        .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect { response ->
                            when (response) {
                                is ResponseWrapper.Error -> {
                                    progressBar.isVisible = false
                                    showToastMessage(response.message ?: getString(R.string.something_went_wrong_try_again))
                                }

                                is ResponseWrapper.Loading -> progressBar.isVisible = true
                                is ResponseWrapper.Success -> {
                                    progressBar.isVisible = false
                                    showToastMessage(getString(R.string.check_your_email))
                                    findNavController().popBackStack()
                                }
                            }

                        }

                }
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}