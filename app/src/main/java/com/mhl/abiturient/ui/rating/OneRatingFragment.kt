package com.mhl.abiturient.ui.rating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mhl.abiturient.R
import com.mhl.abiturient.databinding.FragmentOneRatingBinding

class OneRatingFragment : Fragment() {

    private var _binding : FragmentOneRatingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOneRatingBinding.inflate(inflater)
        return binding.root
    }
}