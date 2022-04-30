package com.mhl.abiturient.ui.about

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mhl.abiturient.R
import com.mhl.abiturient.classes.FirebaseThings
import com.mhl.abiturient.classes.Professions
import com.mhl.abiturient.classes.ProfsAdapter
import com.mhl.abiturient.databinding.FragmentProfessionsBinding

class ProfessionsFragment : Fragment() {
    private var _binding: FragmentProfessionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfessionsBinding.inflate(inflater, container, false)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_professionsFragment_to_aboutFragment)
            }
        })
        var professions = ArrayList<Professions>()
        FirebaseThings().instanceProfs().get().addOnSuccessListener {
            if (it.exists()) {
                for (snap in it.children) {
                    professions.add(snap.getValue(Professions::class.java)!!)
                }
                var adapter = ProfsAdapter(requireContext(), professions)
                adapter.setOnItemClickListener(object : ProfsAdapter.onItemClickListener {

                    override fun onItemClick(position: Int) {
                        var prefs = requireActivity().getSharedPreferences(
                            "Profession",
                            Context.MODE_PRIVATE
                        )
                        prefs.edit().putInt("position", position).apply()
                        findNavController().navigate(R.id.action_professionsFragment_to_oneProfessionFragment)
                    }

                })
                binding.profsRecycler.adapter = adapter
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}