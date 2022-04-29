package com.mhl.abiturient.ui.documents.notentered

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mhl.abiturient.classes.User
import com.mhl.abiturient.R
import com.mhl.abiturient.classes.Authorization
import com.mhl.abiturient.classes.FirebaseThings
import com.mhl.abiturient.classes.Registration
import com.mhl.abiturient.databinding.FragmentRegistrationBinding
import com.vicmikhailau.maskededittext.MaskedFormatter
import com.vicmikhailau.maskededittext.MaskedWatcher

class RegistrationFragment : Fragment() {

    private var binding: FragmentRegistrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        var reg = Registration()


        var maskFormatter = MaskedFormatter("##.##.####")
        binding!!.registrationBirthday.addTextChangedListener(
            MaskedWatcher(
                maskFormatter,
                binding!!.registrationBirthday
            )
        )
        binding!!.registrationBirthday.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length == 10 && !reg.validateDate(p0.toString())) {
                    binding!!.registrationBirthday.error = "Вы ввели неверную дату"
                    binding!!.registrationButton.isClickable = false
                } else if (p0!!.length == 10 && reg.validateDate(p0.toString())) {
                    binding!!.registrationButton.isClickable = true
                }

            }

        })

        binding!!.registrationPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (!Authorization().checkPassword(p0.toString())) {
                    binding!!.registrationButton.isClickable = false
                    binding!!.registrationPassword.error =
                        "В пароле меньше 6 символов, либо больше 24"
                }
            }

        })


        binding!!.registrationButton.setOnClickListener {
            val birthdayString = binding!!.registrationBirthday.text.toString()
            val emailString = binding!!.registrationEmail.text.toString()
            val nameString = binding!!.registrationName.text.toString()
            val surnameString = binding!!.registrationSurname.text.toString()
            val patronymicString = binding!!.registrationPatronymic.text.toString()
            val passwordString = binding!!.registrationPassword.text.toString()
            val rePasswordString = binding!!.registrationRePassword.text.toString()

            if (reg.validateInitials(nameString)
                && reg.validateInitials(surnameString)
                && reg.validateInitials(patronymicString)
                && reg.validateRepeatPassword(passwordString, rePasswordString)
                && Authorization().checkEmail(emailString)
                && reg.validateDate(birthdayString)
            ) {
                Firebase.auth.createUserWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener { it0 ->
                        if (it0.isSuccessful) {

                            Firebase.auth.signInWithEmailAndPassword(emailString, passwordString)
                                .addOnCompleteListener { it1 ->
                                    if (it1.isSuccessful) {
                                        FirebaseThings().instanceUsers()
                                            .child(Firebase.auth.currentUser!!.uid).setValue(
                                            User(
                                                birthday = birthdayString,
                                                email = emailString,
                                                name = nameString,
                                                surname = surnameString,
                                                patronymic = patronymicString
                                            )
                                        ).addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                findNavController().navigate(R.id.action_registrationFragment_to_signedFragment)
                                            }
                                        }

                                    }
                                }

                        } else {
                            AlertDialog.Builder(requireContext())
                                .setMessage("Пользователь уже зарегистрирован")
                                .create()
                                .show()
                        }
                    }
            }
        }
        return binding!!.root
    }
}