package com.mhl.abiturient.ui.documents.entered

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.mhl.abiturient.classes.User
import com.mhl.abiturient.R
import com.mhl.abiturient.classes.Authorization
import com.mhl.abiturient.classes.FirebaseThings
import com.mhl.abiturient.classes.Registration
import com.mhl.abiturient.databinding.FragmentSignedBinding
import com.vicmikhailau.maskededittext.MaskedFormatter
import com.vicmikhailau.maskededittext.MaskedWatcher

class SignedFragment : Fragment() {
    private var _binding: FragmentSignedBinding? = null
    private val binding get() = _binding!!
    
    private var imageUri: Uri? = null
    private var doc = ""
    private var url = ""
    private var buttonClicked = ""

    private var userData: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignedBinding.inflate(inflater)
        var maskFormatter = MaskedFormatter("##.##.####")
        binding.signedBirthday.addTextChangedListener(
            MaskedWatcher(
                maskFormatter,
                binding.signedBirthday
            )
        )
        FirebaseThings().instanceUsers().child(Firebase.auth.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                userData = it.getValue(User::class.java)!!
                binding.signedEmail.text = userData!!.email
                binding.signedName.setText(userData!!.name)
                binding.signedSurname.setText(userData!!.surname)
                binding.signedPatronymic.setText(userData!!.patronymic)
                binding.signedBirthday.setText(userData!!.birthday)

                if (userData!!.certificate!!.isNotEmpty()) {
                    binding.certificateIsAdded.text = "Документ добавлен"
                }
                if (userData!!.passport!!.isNotEmpty()) {
                    binding.passportIsAdded.text = "Документ добавлен"
                }
                if (userData!!.snils!!.isNotEmpty()) {
                    binding.snilsIsAdded.text = "Документ добавлен"
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_signedFragment_to_navigation_home2)
            }
        })

        binding.changePasswordButton.setOnClickListener {
            var view = layoutInflater.inflate(R.layout.change_password_view, null)
            AlertDialog.Builder(requireContext())
                .setView(view)
                .setPositiveButton("Изменить",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        var password = view.findViewById<EditText>(R.id.dialogPassword)
                        var rePassword = view.findViewById<EditText>(R.id.dialogRePassword)
                        if (Authorization().checkPassword(password.text.toString())
                            && Registration().validateRepeatPassword(
                                password.text.toString(),
                                rePassword.text.toString()
                            )
                        ) {
                            Firebase.auth.currentUser!!.updatePassword(password.text.toString())
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Пароль успешно изменён",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                        else {
                            Toast.makeText(
                                requireContext(),
                                "Пароль должен быть больше 5 символов и меньше 25 символов",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                .create()
                .show()
        }

        binding.signedAddPassport.setOnClickListener {
            selectImage("passport")
            buttonClicked = "passport"
        }
        binding.signedAddSnils.setOnClickListener {
            selectImage("snils")
            buttonClicked = "snils"
        }
        binding.signedAddCertificate.setOnClickListener {
            selectImage("certificate")
            buttonClicked = "certificate"
        }
        binding.logoutButton.setOnClickListener {
            Firebase.auth.signOut()
            findNavController().navigate(R.id.action_signedFragment_to_signInFragment)
        }
        binding.changeButton.setOnClickListener {
            var nameString = binding.signedName.text.toString()
            var surnameString = binding.signedSurname.text.toString()
            var patronymicString = binding.signedPatronymic.text.toString()
            var birthdayString = binding.signedBirthday.text.toString()

            if (nameString != ""
                && surnameString != ""
                && patronymicString != ""
                && Registration().validateDate(birthdayString)
                && userData != null
            ) {
                userData!!.birthday = birthdayString
                userData!!.name = nameString
                userData!!.patronymic = patronymicString
                userData!!.surname = surnameString
                FirebaseThings().instanceUsers().child(Firebase.auth.uid!!).setValue(userData)

                AlertDialog.Builder(requireContext())
                    .setMessage("Данные изменены")
                    .create()
                    .show()
            } else {
                AlertDialog.Builder(requireContext())
                    .setMessage("Одно или несколько полей имеют некорректный формат")
                    .create()
                    .show()
            }
        }



        return binding.root
    }


    private fun selectImage(document: String) {
        doc = document
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            if (imageUri != null) {
                var storage = FirebaseStorage.getInstance()
                    .getReference("images/${Firebase.auth.currentUser!!.uid}/$doc")
                storage.putFile(imageUri!!)
                    .addOnSuccessListener {
                        storage.downloadUrl.addOnSuccessListener {
                            url = it.toString()
                            FirebaseThings().instanceUsers().child(Firebase.auth.uid!!).child(doc)
                                .setValue(url).addOnSuccessListener {
                                    when (buttonClicked) {
                                        "passport" -> binding.passportIsAdded.text =
                                            "Документ добавлен"
                                        "snils" -> binding.snilsIsAdded.text = "Документ добавлен"
                                        "certificate" -> binding.certificateIsAdded.text =
                                            "Документ добавлен"
                                    }
                                    AlertDialog.Builder(requireContext())
                                        .setMessage("Документ загружен")
                                        .create()
                                        .show()
                                }
                        }
                    }
            } else {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}