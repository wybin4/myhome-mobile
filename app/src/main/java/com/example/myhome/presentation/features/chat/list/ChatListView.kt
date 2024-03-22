package com.example.myhome.presentation.features.chat.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.MainViewModel
import com.example.myhome.R
import com.example.myhome.databinding.ChatListItemBinding
import com.example.myhome.databinding.ChatListItemLoadingBinding
import com.example.myhome.databinding.ChatListViewBinding
import com.example.myhome.presentation.ConstantsUi
import com.example.myhome.presentation.features.chat.ChatMapper
import com.example.myhome.presentation.features.chat.models.ChatUiModel
import com.example.myhome.presentation.utils.adapters.CustomListAdapter
import com.example.myhome.presentation.utils.adapters.InfiniteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatListView : Fragment() {
    private var _binding: ChatListViewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var chatListAdapter: CustomListAdapter<ChatUiModel, ChatListItemBinding>
    private lateinit var chatInfiniteListAdapter: InfiniteListAdapter<String, ChatListItemLoadingBinding>

    private val chatMapper = ChatMapper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatListViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupInfiniteRecyclerView()

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_chatListView_to_chatAddView)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        observeList()
        observeResourceState()
    }

    private fun setupInfiniteRecyclerView() {
        chatInfiniteListAdapter = InfiniteListAdapter(
            itemList = List(ConstantsUi.VERTICAL_LOADING_RECYCLER_VIEW_SIZE) { "" },
            itemBindingInflater = { inflater, parent, attachToParent ->
                ChatListItemLoadingBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.item = item
            }
        )
        binding.chatInfiniteRecyclerView.adapter = chatInfiniteListAdapter
        binding.chatInfiniteRecyclerView.hasFixedSize()
    }

    private fun observeList() {
        viewModel.chatList.observe(viewLifecycleOwner) { chatListAdapter.submitList(it) }
    }

    private fun observeResourceState() {
        viewModel.chatListState.observe(viewLifecycleOwner) { state ->
            binding.apply {
                onLoading.visibility = state.loadingVisibility
                onSuccess.visibility = state.successVisibility
                onEmpty.visibility = state.emptyVisibility
                onError.visibility = state.errorVisibility
                addButton.visibility = state.addButtonVisibility
                state.errorMessage?.let { errorLayout.error = it }
            }
        }
    }

    private fun setupRecyclerView() {
        chatListAdapter = CustomListAdapter(
            itemBindingInflater = { inflater, parent, attachToParent ->
                ChatListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.chat = item
            },
            onItemClick = { item ->
                val bundle = chatMapper.chatListToGetParcel(item).toBundle()
                findNavController().navigate(R.id.action_chatListView_to_chatGetView, bundle)
            }
        )

        binding.chatRecyclerView.adapter = chatListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}