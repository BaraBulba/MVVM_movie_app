package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.andrew.solokhov.mvvmmovieapp.R
import com.andrew.solokhov.mvvmmovieapp.databinding.FragmentSearchBinding
import com.andrew.solokhov.mvvmmovieapp.presentation.navigation.NewOnBackPress
import com.andrew.solokhov.mvvmmovieapp.presentation.navigation.PopUpNavOptions
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), NewOnBackPress.NewOnBackPressCallback {

    private var _binding: FragmentSearchBinding? = null

    private val customOnBackPress by lazy { NewOnBackPress(this) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        _binding?.run {
            tvTest.text = "Search"
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, customOnBackPress)
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