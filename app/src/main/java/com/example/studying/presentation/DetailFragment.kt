package com.example.studying.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.studying.databinding.FragmentDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    private var title = ""
    private var id = 0

    private val viewModel: DetailViewModel by viewModel()

    private val adapter = TasksAdapter {
        viewModel.delete(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = requireArguments().getString(TITLE_KEY, "")
        id = requireArguments().getInt(ID_KEY, 0)

        binding.noteTitle.text = title

        adapter.addDelegate(AdapterDelegateTasks.Task())
        binding.tasks.adapter = adapter

        viewModel.fetchTasks(id)

        binding.sendBtn.setOnClickListener {
            val text = binding.input.text.toString()
            if (text.isNotEmpty()) viewModel.addTask(text, id)
            binding.input.setText("")
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.tvEmpty.visibility = if (it is TaskUiState.Empty) View.VISIBLE else View.GONE
            binding.tasks.visibility = if (it is TaskUiState.Success) View.VISIBLE else View.GONE
            if (it is TaskUiState.Success) adapter.submitList(it.data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TITLE_KEY = "TITLE_KEY"
        private const val ID_KEY = "ID_KEY"
        fun newInstance(title: String, id: Int) =
            DetailFragment().apply {
                arguments = bundleOf().apply {
                    putString(TITLE_KEY, title)
                    putInt(ID_KEY, id)
                }
            }
    }
}