package com.mhl.abiturient.ui.about

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.mhl.abiturient.R
import com.mhl.abiturient.classes.FirebaseThings
import com.mhl.abiturient.classes.Professions
import com.mhl.abiturient.classes.ProfsAdapter
import com.mhl.abiturient.databinding.FragmentOneProfessionBinding

class OneProfessionFragment : Fragment() {

    private var _binding : FragmentOneProfessionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOneProfessionBinding.inflate(inflater)

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_oneProfessionFragment_to_professionsFragment)
            }
        })

        var position = requireActivity().getSharedPreferences("Profession", Context.MODE_PRIVATE).getInt("position", 0)
        FirebaseThings().instanceProfs().get().addOnSuccessListener {
            if (it.exists()){
                var data = ArrayList<Professions>()
                for (snap in it.children){
                    data.add(snap.getValue(Professions::class.java)!!)
                }
                binding.professionAbout.text = data[position].about
                binding.professionBudget.text = binding.professionBudget.text.toString() + data[position].budget
                binding.professionCode.text = binding.professionCode.text.toString() + data[position].code
                binding.professionName.text = binding.professionName.text.toString() + data[position].name
            }
        }

        return binding.root
    }
}