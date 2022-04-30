package com.mhl.abiturient.ui.rating

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.mhl.abiturient.R
import com.mhl.abiturient.classes.FirebaseThings
import com.mhl.abiturient.classes.Professions
import com.mhl.abiturient.classes.Years
import com.mhl.abiturient.databinding.FragmentOneRatingBinding

class OneRatingFragment : Fragment() {

    private var _binding : FragmentOneRatingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOneRatingBinding.inflate(inflater)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_oneRatingFragment_to_navigation_rating)
            }
        })

        var name = requireActivity().getSharedPreferences("Profession", Context.MODE_PRIVATE).getString("name", "")
        var year = requireActivity().getSharedPreferences("Profession", Context.MODE_PRIVATE).getInt("year", 0)


        FirebaseThings().instanceProfs().child("${name!!}/years/${year}").get().addOnSuccessListener {
            Log.d("SUCCESS", "SUCCESS")
            var year = it.getValue(Years::class.java)!!
            var webView = WebViewClient()
            binding.ratingView.webViewClient = webView
            binding.ratingView.loadUrl(year.url!!)
            binding.ratingView.settings.javaScriptEnabled = true
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}