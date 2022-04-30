package com.mhl.abiturient.ui.rating

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
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
import com.mhl.abiturient.databinding.FragmentRatingBinding

class RatingFragment : Fragment() {

    private var _binding: FragmentRatingBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_navigation_rating_to_navigation_home)
            }
        })

        var professions = ArrayList<Professions>()
        FirebaseThings().instanceProfs().get().addOnSuccessListener {
            if (it.exists()){
                for (snap in it.children){
                    professions.add(snap.getValue(Professions::class.java)!!)
                }
                var adapter = ProfsAdapter(requireContext(), professions)
                adapter.setOnItemClickListener(object : ProfsAdapter.onItemClickListener{

                    override fun onItemClick(position: Int) {
                        var prefs = requireActivity().getSharedPreferences("Profession", Context.MODE_PRIVATE)
                        prefs.edit().putString("name", professions[position].name).apply()
                        if (professions[position].years!!.size <=  1) {
                            AlertDialog.Builder(requireContext())
                                .setMessage("Выберите год")
                                .setPositiveButton(professions[position].years!![0].year) { _, _ ->
                                    prefs.edit().putInt("year", 0).apply()
                                    findNavController().navigate(R.id.action_professionsFragment_to_oneProfessionFragment)
                                }
                                .create()
                                .show()
                        }
                        else {
                            AlertDialog.Builder(requireContext())
                                .setMessage("Выберите год")
                                .setPositiveButton(professions[position].years!![0].year) { _, _ ->
                                    prefs.edit().putInt("year", 0).apply()
                                    findNavController().navigate(R.id.action_navigation_rating_to_oneRatingFragment)
                                }
                                .setNegativeButton(professions[position].years!![1].year) { _,_ ->
                                    prefs.edit().putInt("year", 1).apply()
                                    findNavController().navigate(R.id.action_navigation_rating_to_oneRatingFragment)
                                }
                                .create()
                                .show()
                        }
                    }

                })
                binding.ratingRecycler.adapter = adapter
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}