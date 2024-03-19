package com.example.myhome.presentation.features.chat.get

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myhome.databinding.ChatGetViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatGetView : Fragment() {
    private var _binding: ChatGetViewBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ChatGetViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatGetViewBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}