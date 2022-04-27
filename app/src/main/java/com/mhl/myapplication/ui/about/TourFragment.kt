package com.mhl.myapplication.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.mhl.myapplication.databinding.FragmentTourBinding

class TourFragment : Fragment() {

    private var binding : FragmentTourBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTourBinding.inflate(inflater)
        var webView = WebViewClient()
        binding!!.tourWeb.webViewClient = webView
        binding!!.tourWeb.loadUrl("http://vt.tpk-1.ru/view.html?rm=10000")
        binding!!.tourWeb.settings.javaScriptEnabled = true
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}