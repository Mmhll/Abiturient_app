package com.mhl.abiturient.ui.documents.notentered

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mhl.abiturient.R
import com.mhl.abiturient.classes.Authorization
import com.mhl.abiturient.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater)

        if (Firebase.auth.currentUser != null){
            findNavController().navigate(R.id.action_signInFragment_to_signedFragment)
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_signInFragment_to_navigation_home)
            }
        })

        binding.authButton.setOnClickListener {
            if (Authorization().auth(
                    binding.loginEmail.text.toString(),
                    binding.loginPassword.text.toString()
                )
            ) {
                Firebase.auth.signInWithEmailAndPassword(
                    binding.loginEmail.text.toString(),
                    binding.loginPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        findNavController().navigate(R.id.action_signInFragment_to_signedFragment)
                    } else {
                        AlertDialog.Builder(requireActivity())
                            .setMessage("Данные не являются правильными")
                            .create()
                            .show()
                    }
                }
            } else {
                AlertDialog.Builder(requireActivity())
                    .setMessage("Email или/и пароль не валидны")
                    .create()
                    .show()
            }
        }

        binding.registrationButton.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_registrationFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}