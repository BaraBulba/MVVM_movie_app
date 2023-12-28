package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth.sign_up

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.databinding.FragmentSignUpBinding
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.NewClickableSpan
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.SignUpFormEvent
import com.andrew.solokhov.mvvmmovieapp.presentation.utils.showToastMessage
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
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
                                Toast.makeText(
                                    requireContext(),
                                    "Successfully signed up!",
                                    Toast.LENGTH_SHORT
                                ).show()
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
            showToastMessage(getString(R.string.terms_and_services_clicked), requireContext())
        }
        privacyPolicyTextClick = NewClickableSpan {
            showToastMessage(getString(R.string.privacy_policy_clicked), requireContext())
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