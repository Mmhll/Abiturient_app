package com.mhl.abiturient.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
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
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/cursach-ee2f0.appspot.com/o/images%2Fabout%2Fvirt.jpg?alt=media&token=863bee1a-9898-461e-9b3d-eac4b09edebc")
            .centerInside()
            .apply(
                RequestOptions().transform(
                    GranularRoundedCorners(30f, 30f, 0f, 0f)
                )
            )
            .into(binding.buttonProfs)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}