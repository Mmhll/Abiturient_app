package com.mhl.myapplication.ui.documents.notentered

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mhl.myapplication.R
import com.mhl.myapplication.classes.Authorization
import com.mhl.myapplication.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private var binding: FragmentSignInBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater)

        if (Firebase.auth.currentUser != null){
            findNavController().navigate(R.id.action_signInFragment_to_signedFragment)
        }


        binding!!.authButton.setOnClickListener {
            if (Authorization().auth(
                    binding!!.loginEmail.text.toString(),
                    binding!!.loginPassword.text.toString()
                )
            ) {
                Firebase.auth.signInWithEmailAndPassword(
                    binding!!.loginEmail.text.toString(),
                    binding!!.loginPassword.text.toString()
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

        binding!!.registrationButton.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_registrationFragment)
        }

        return binding!!.root
    }
}