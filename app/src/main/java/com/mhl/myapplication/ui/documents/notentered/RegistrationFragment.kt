package com.mhl.myapplication.ui.documents.notentered

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.mhl.myapplication.classes.Registration
import com.mhl.myapplication.databinding.FragmentRegistrationBinding
import com.vicmikhailau.maskededittext.MaskedFormatter
import com.vicmikhailau.maskededittext.MaskedWatcher
import java.time.LocalDate
import java.util.*

class RegistrationFragment : Fragment() {

    private var binding : FragmentRegistrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        var maskFormatter = MaskedFormatter("##.##.####")
        binding!!.registrationBirthday.addTextChangedListener(MaskedWatcher(maskFormatter, binding!!.registrationBirthday))
        binding!!.registrationBirthday.addTextChangedListener(object : TextWatcher  {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length == 10 && !Registration().validateDate(p0.toString())){
                    Log.e("JOPAAAA", p0.toString())
                }

            }

        })
        return binding!!.root
    }
}