package com.mhl.abiturient.ui.about

import android.os.Bundle
import android.os.CountDownTimer
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
import java.lang.IllegalStateException
import kotlin.jvm.Throws

class AboutFragment : Fragment() {

    private var binding: FragmentAboutBinding? = null

    @Throws(IllegalStateException::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_aboutFragment_to_navigation_home)
            }
        })
        object : CountDownTimer(100, 100) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                context!!.let {

                    binding!!.buttonTour.setOnClickListener {
                        findNavController().navigate(R.id.action_aboutFragment_to_tourFragment)
                    }
                    binding!!.buttonProfs.setOnClickListener {
                        findNavController().navigate(R.id.action_aboutFragment_to_professionsFragment)
                    }
                }
            }

        }.start()



        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}