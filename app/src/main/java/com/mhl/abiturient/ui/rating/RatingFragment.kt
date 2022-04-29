package com.mhl.abiturient.ui.rating

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

    // This property is only valid between onCreateView and
    // onDestroyView.
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
                        prefs.edit().putInt("position", position).apply()
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