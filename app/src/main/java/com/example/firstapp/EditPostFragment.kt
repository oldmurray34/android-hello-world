package com.example.firstapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.firstapp.databinding.FragmentEditPostBinding


class EditPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditPostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by activityViewModels()

        binding.content.setText(viewModel.edited.value?.content)
        binding.content.requestFocus()

        binding.save.setOnClickListener {
            val content = binding.content.text?.toString()
            if (!content.isNullOrBlank()) {
                viewModel.changeContent(content)
                viewModel.save()
                AndroidUtils.hideKeyboard(binding.root)
            }
            findNavController().navigateUp()
        }
        return binding.root
    }
}