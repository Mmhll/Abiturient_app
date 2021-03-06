package com.mhl.abiturient.ui.rating

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mhl.abiturient.R
import com.mhl.abiturient.classes.FirebaseThings
import com.mhl.abiturient.classes.Professions
import com.mhl.abiturient.classes.ProfsAdapter
import com.mhl.abiturient.databinding.FragmentRatingBinding
import java.lang.IllegalStateException
import java.lang.NullPointerException
import kotlin.jvm.Throws

class RatingFragment : Fragment() {

    private var binding: FragmentRatingBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRatingBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_navigation_rating_to_navigation_home)
            }
        })

        var professions = ArrayList<Professions>()
        FirebaseThings().instanceProfs().get().addOnSuccessListener {
            if (it.exists()) {
                for (snap in it.children) {
                    professions.add(snap.getValue(Professions::class.java)!!)
                }
                var adapter = ProfsAdapter(this, professions)
                adapter.setOnItemClickListener(object : ProfsAdapter.onItemClickListener {

                    override fun onItemClick(position: Int) {
                        var prefs = requireActivity().getSharedPreferences(
                            "Profession",
                            Context.MODE_PRIVATE
                        )
                        prefs.edit().putString("name", professions[position].name).apply()
                        if (professions[position].years!!.size <= 1) {
                            AlertDialog.Builder(requireContext())
                                .setMessage("???????????????? ??????")
                                .setPositiveButton(professions[position].years!![0].year) { _, _ ->
                                    prefs.edit().putInt("year", 0).apply()
                                    findNavController().navigate(R.id.action_navigation_rating_to_oneRatingFragment)
                                }
                                .create()
                                .show()
                        } else {
                            AlertDialog.Builder(requireContext())
                                .setMessage("???????????????? ??????")
                                .setPositiveButton(professions[position].years!![0].year) { _, _ ->
                                    prefs.edit().putInt("year", 0).apply()
                                    findNavController().navigate(R.id.action_navigation_rating_to_oneRatingFragment)
                                }
                                .setNegativeButton(professions[position].years!![1].year) { _, _ ->
                                    prefs.edit().putInt("year", 1).apply()
                                    findNavController().navigate(R.id.action_navigation_rating_to_oneRatingFragment)
                                }
                                .create()
                                .show()
                        }
                    }

                })
                try {

                    binding!!.ratingRecycler.adapter = adapter
                }
                catch (e : NullPointerException){

                }
            }
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}