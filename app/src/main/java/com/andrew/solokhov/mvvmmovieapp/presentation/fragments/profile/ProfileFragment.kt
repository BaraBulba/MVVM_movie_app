package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.databinding.FragmentProfileBinding
import com.andrew.solokhov.mvvmmovieapp.presentation.navigation.NewOnBackPress
import com.andrew.solokhov.mvvmmovieapp.presentation.navigation.PopUpNavOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile), NewOnBackPress.NewOnBackPressCallback {


    private var _binding: FragmentProfileBinding? = null
    private val newOnBackPress by lazy { NewOnBackPress(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        _binding?.run {
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, newOnBackPress)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun newOnBackPressCallback() {
        val btmNavBar = requireActivity()
            .findViewById<ChipNavigationBar>(R.id.bottom_nav_bar)
        btmNavBar.setItemSelected(R.id.homeFragment)
        val navOptions = PopUpNavOptions(findNavController()).navOptions
        findNavController().navigate(R.id.homeFragment, null, navOptions)
    }
}