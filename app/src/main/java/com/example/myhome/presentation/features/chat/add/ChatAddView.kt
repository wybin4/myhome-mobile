package com.example.myhome.presentation.features.chat.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myhome.databinding.ChatAddListViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatAddView : Fragment() {
    private var _binding: ChatAddListViewBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ChatAddViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatAddListViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}