package com.example.myhome.presentation.features.chat.get

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.ChatGetViewBinding
import com.example.myhome.databinding.MessageListItemBinding
import com.example.myhome.features.CommonSocketService
import com.example.myhome.presentation.features.chat.adapters.InfiniteMessageListAdapter
import com.example.myhome.presentation.features.chat.models.MessageUiModel
import com.example.myhome.presentation.state.list.ListState
import com.example.myhome.presentation.state.list.ListStateManager
import com.example.myhome.presentation.utils.adapters.CustomListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatGetView : Fragment() {
    private var _binding: ChatGetViewBinding? = null
    private val binding get() = _binding!!

    private var isServiceBound = false

    private val viewModel by viewModels<ChatGetViewModel>()

    private lateinit var messageListAdapter: CustomListAdapter<MessageUiModel, MessageListItemBinding>
    private lateinit var messageInfiniteListAdapter: InfiniteMessageListAdapter

    private val listStateManager = ListStateManager(this::updateViewState)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatGetViewBinding.inflate(inflater, container, false)
        viewModel.chatParcelable = requireArguments().getParcelable("chat")!!
        binding.chat = viewModel.chatParcelable
        binding.lifecycleOwner = this

        val serviceIntent = Intent(requireContext(), CommonSocketService::class.java)
        requireContext().bindService(serviceIntent, viewModel.getServiceConnection(), Context.BIND_AUTO_CREATE)

        setupRecyclerView()
        setupInfiniteRecyclerView()

        binding.backButton.setOnClickListener {
            navigateBack()
        }
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        binding.sendMessageButton.setOnClickListener {
            val enteredText = binding.enteredText.text.toString()
            if (enteredText.length in 1..999) {
                viewModel.sendMessage(enteredText)
            } else if (enteredText.length > 999) {
                val messageChunks = enteredText.chunked(1000)
                messageChunks.forEach { chunk ->
                    viewModel.sendMessage(chunk)
                }
            }
            binding.enteredText.setText("")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeList()
        observeResourceState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchMessageList()
    }

    override fun onStart() {
        super.onStart()
        viewModel.isItView = true
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_chatGetView_to_chatListView)
    }

    private fun updateViewState(state: ListState) {
        binding.apply {
            messageInfiniteRecyclerView.visibility = state.loadingVisibility
            messageRecyclerView.visibility = state.successVisibility
            onEmpty.visibility = state.emptyVisibility
            onError.visibility = state.errorVisibility
            state.errorMessage?.let { errorLayout.error = it }
        }
    }

    private fun setupInfiniteRecyclerView() {
        messageInfiniteListAdapter = InfiniteMessageListAdapter(
            itemList = listOf("left", "left", "left", "right", "left"),
        )
        binding.messageInfiniteRecyclerView.adapter = messageInfiniteListAdapter
        binding.messageInfiniteRecyclerView.hasFixedSize()
    }

    private fun observeList() {
        viewModel.messageList.observe(viewLifecycleOwner) { newList ->
            messageListAdapter.submitList(newList)
            binding.messageRecyclerView.scrollToPosition(newList.size - 1)
        }
    }

    private fun observeResourceState() {
        viewModel.messageListState.observe(viewLifecycleOwner) { resource ->
            listStateManager.observeStates(resource)
        }
    }

    private fun setupRecyclerView() {
        messageListAdapter = CustomListAdapter(
            itemBindingInflater = { inflater, parent, attachToParent ->
                MessageListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { bindingList, item ->
                bindingList.message = item
            }, onItemClick = null
        )
        binding.messageRecyclerView.adapter = messageListAdapter
        binding.messageRecyclerView.itemAnimator = null
    }

    override fun onPause() {
        super.onPause()
        viewModel.isItView = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (isServiceBound) {
            requireContext().unbindService(viewModel.getServiceConnection())
            isServiceBound = false
        }
    }
}