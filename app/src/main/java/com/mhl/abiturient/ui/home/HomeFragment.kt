package com.mhl.abiturient.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mhl.abiturient.R
import com.mhl.abiturient.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonAbout.setOnClickListener{
            it.findNavController().navigate(R.id.action_navigation_home_to_navigation_dashboard)
        }
        binding.buttonRating.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_home_to_navigation_notifications)
        }
        binding.buttonDocs.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_home_to_signInFragment)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}