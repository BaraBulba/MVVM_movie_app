package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.databinding.FragmentHomeBinding
import com.andrew.solokhov.mvvmmovieapp.presentation.navigation.NewOnBackPress
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), NewOnBackPress.NewOnBackPressCallback {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private var _binding: FragmentHomeBinding? = null

    private val customOnBackPress by lazy { NewOnBackPress(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        _binding?.run {
            tvTest.text = firebaseAuth.currentUser?.displayName
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, customOnBackPress)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun newOnBackPressCallback() {
        activity?.finish()
    }
}