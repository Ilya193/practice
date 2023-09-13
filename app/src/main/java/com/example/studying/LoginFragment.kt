package com.example.studying

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.studying.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    private lateinit var listeners: Listeners
    private var name: String = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Listeners)
            listeners = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        settingListeners()
    }

    private fun settingListeners() {
        binding.textView.setOnClickListener {
            listeners.onCLick(name)
        }

        binding.root.setOnClickListener {
            setFragmentResult("CAR", bundleOf("CAR" to "Nissan"))
            parentFragmentManager.popBackStack()
        }
    }

    private fun parseParams() {
        val bundle = arguments
        if (bundle != null) {
            name = bundle.getString(NAME_USER) ?: ""
            binding.textView.text = name
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val NAME_USER = "NAME_USER"

        fun newInstance(name: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME_USER, name)
                }
            }
    }
}