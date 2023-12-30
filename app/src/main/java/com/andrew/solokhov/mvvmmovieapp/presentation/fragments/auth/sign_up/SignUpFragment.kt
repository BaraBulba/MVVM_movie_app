package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth.sign_up

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.databinding.FragmentSignUpBinding
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.NewClickableSpan
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.SignUpFormEvent
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.removeKeyboard
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.showToastMessage
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private var _binding: FragmentSignUpBinding? = null
    private var termsOfUseTextClick: NewClickableSpan? = null
    private var privacyPolicyTextClick: NewClickableSpan? = null

    private val viewModel by viewModels<SignUpViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        _binding?.run {
            changeTextColorPartly()
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            setupForm()
            collectData()
        }
    }

    private fun FragmentSignUpBinding.collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.validationEvents.collectLatest { event ->
                        when (event) {
                            is SignUpViewModel.ValidationEvent.Success -> {
                                val email = emailTextInput.text.toString()
                                val password = passwordTextInput.text.toString()
                                val fullName = fullNameTextInput.text.toString()
                                viewModel.signUpNewUser(email, password, fullName = fullName)
                            }
                        }
                    }
                }

                launch {
                    viewModel.validationState.collectLatest {
                        if (!emailTextInput.isFocused) {
                            emailTextInput.setText(it.email)
                        }
                        if (!passwordTextInput.isFocused) {
                            passwordTextInput.setText(it.password)
                        }
                        if (!fullNameTextInput.isFocused) {
                            fullNameTextInput.setText(it.fullName)
                        }
                        emailTextInputLayout.error = it.emailError
                        passwordTextInputLayout.error = it.passwordError
                        fullNameTextInputLayout.error = it.fullNameError

                        checkBox.isChecked = it.acceptedTermsAndPolicy

                        it.termsAndPolicyError?.let { errorMessage ->
                            if (!checkBox.isChecked) {
                                Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                }
                launch {
                    viewModel.signUpState.collectLatest { result ->
                        when (result) {
                            SignUpResult.Empty -> Unit
                            is SignUpResult.Error -> {
                                progressBar.isVisible = false
                                showToastMessage(
                                    getString(R.string.something_went_wrong_try_again),
                                    requireContext().applicationContext
                                )
                            }

                            SignUpResult.Loading -> progressBar.isVisible = true
                            is SignUpResult.Success -> {
                                progressBar.isVisible = false
                                findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun FragmentSignUpBinding.setupForm() {
        emailTextInput.addTextChangedListener {
            if (emailTextInput.isFocused) {
                viewModel.onSignUpEvent(SignUpFormEvent.EmailChanged(it.toString()))
            }
        }
        passwordTextInput.addTextChangedListener {
            if (passwordTextInput.isFocused) {
                viewModel.onSignUpEvent(SignUpFormEvent.PasswordChanged(it.toString()))
            }
        }
        fullNameTextInput.addTextChangedListener {
            if (fullNameTextInput.isFocused) {
                viewModel.onSignUpEvent(SignUpFormEvent.FullNameChanged(it.toString()))
            }
        }
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onSignUpEvent(SignUpFormEvent.AcceptTermsAndPolicy(isChecked))
        }
        btnSignUp.setOnClickListener {
            with(requireView()){
                removeKeyboard()
            }
            viewModel.onSignUpEvent(SignUpFormEvent.SignUp)
        }
    }

    private fun FragmentSignUpBinding.changeTextColorPartly() {
        val fullText = getString(R.string.terms_of_use)
        val ssBuilder = SpannableStringBuilder(fullText)
        val color = ContextCompat.getColor(requireContext(), R.color.light_blue)
        val colorSpan = ForegroundColorSpan(color)
        val colorSpan2 = ForegroundColorSpan(color)
        termsOfUseTextClick = NewClickableSpan {
            showToastMessage(getString(R.string.terms_and_services_clicked), requireContext().applicationContext)
        }
        privacyPolicyTextClick = NewClickableSpan {
            showToastMessage(getString(R.string.privacy_policy_clicked), requireContext().applicationContext)
        }

        ssBuilder.setSpan(termsOfUseTextClick, 15, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssBuilder.setSpan(privacyPolicyTextClick, 38, 52, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssBuilder.setSpan(colorSpan, 38, 52, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssBuilder.setSpan(colorSpan2, 15, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvTermsAndServicesPlusPolicy.text = ssBuilder
        tvTermsAndServicesPlusPolicy.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        termsOfUseTextClick = null
        privacyPolicyTextClick = null
        _binding = null
    }

}