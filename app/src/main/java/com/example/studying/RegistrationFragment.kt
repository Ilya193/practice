package com.example.studying

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.studying.databinding.FragmentRegistationBinding
import com.google.android.material.snackbar.Snackbar


class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistationBinding? = null
    private val binding: FragmentRegistationBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("car") { _, bundle ->
            val result = bundle.getString("nameCar") ?: ""
            binding.textView.text = result
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegistationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResult("actionBack", bundleOf("state" to false))
        binding.textView.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, LoginFragment.newInstance("NAME"))
                .addToBackStack(null)
                .commit()
        }
        Log.d("attadag", "onViewCreated registration")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("attadag", "onDestroyView registration")
    }

    companion object {
        fun newInstance() =
            RegistrationFragment()
    }
}