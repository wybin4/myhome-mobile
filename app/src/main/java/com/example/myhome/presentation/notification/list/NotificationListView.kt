package com.example.myhome.presentation.notification.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myhome.databinding.NotificationListViewBinding

class NotificationListView : Fragment() {
    private var _binding: NotificationListViewBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this)[NotificationListViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NotificationListViewBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}