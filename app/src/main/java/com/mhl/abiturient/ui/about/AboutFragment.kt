package com.mhl.abiturient.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mhl.abiturient.R
import com.mhl.abiturient.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_aboutFragment_to_navigation_home)
            }
        })
        /*Glide.with(this)
            .load(R.drawable.virt)
            .apply(
                RequestOptions().transform(
                    GranularRoundedCorners(100f, 100f, 0f, 0f)
                )
            ).into(binding.buttonTour)
        Glide.with(this)
            .load(R.drawable.img)
            .apply(
                RequestOptions().transform(
                    GranularRoundedCorners(100f, 100f, 0f, 0f)
                )
            ).into(binding.buttonProfs)*/

        binding.buttonTour.setOnClickListener {
            findNavController().navigate(R.id.action_aboutFragment_to_tourFragment)
        }
        binding.buttonProfs.setOnClickListener {
            findNavController().navigate(R.id.action_aboutFragment_to_professionsFragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}