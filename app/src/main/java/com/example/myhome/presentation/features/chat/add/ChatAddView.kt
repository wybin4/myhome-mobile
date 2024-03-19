package com.example.myhome.presentation.features.chat.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.ChatAddListItemBinding
import com.example.myhome.databinding.ChatAddListItemLoadingBinding
import com.example.myhome.databinding.ChatAddListViewBinding
import com.example.myhome.presentation.ConstantsUi
import com.example.myhome.presentation.features.chat.models.ReceiverUiModel
import com.example.myhome.presentation.state.list.ListState
import com.example.myhome.presentation.state.list.ListStateManager
import com.example.myhome.presentation.utils.adapters.CustomListAdapter
import com.example.myhome.presentation.utils.adapters.InfiniteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatAddView : Fragment() {
    private var _binding: ChatAddListViewBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ChatAddViewModel>()

    private lateinit var chatListAdapter: CustomListAdapter<ReceiverUiModel, ChatAddListItemBinding>
    private lateinit var chatInfiniteListAdapter: InfiniteListAdapter<String, ChatAddListItemLoadingBinding>

    private val listStateManager = ListStateManager(this::updateViewState)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatAddListViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupInfiniteRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeList()
        observeResourceState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchChatList()
    }

    private fun updateViewState(state: ListState) {
        binding.apply {
            onLoading.visibility = state.loadingVisibility
            onSuccess.visibility = state.successVisibility
            onEmpty.visibility = state.emptyVisibility
            onError.visibility = state.errorVisibility
            state.errorMessage?.let { errorLayout.error = it }
        }
    }

    private fun setupInfiniteRecyclerView() {
        chatInfiniteListAdapter = InfiniteListAdapter(
            itemList = List(ConstantsUi.VERTICAL_LOADING_RECYCLER_VIEW_SIZE) { "" },
            itemBindingInflater = { inflater, parent, attachToParent ->
                ChatAddListItemLoadingBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.item = item
            }
        )
        binding.receiverInfiniteRecyclerView.adapter = chatInfiniteListAdapter
        binding.receiverInfiniteRecyclerView.hasFixedSize()
    }

    private fun observeList() {
        viewModel.receiverList.observe(viewLifecycleOwner) { chatListAdapter.submitList(it) }
        viewModel.chatAddData.observe(viewLifecycleOwner) {
            val bundle = viewModel.addToGetParcel(it).toBundle()
            findNavController().popBackStack()
            findNavController().navigate(R.id.action_chatListView_to_chatGetView, bundle)
        }
    }

    private fun observeResourceState() {
        viewModel.receiverListState.observe(viewLifecycleOwner) { resource ->
            listStateManager.observeStates(resource)
        }
    }

    private fun setupRecyclerView() {
        chatListAdapter = CustomListAdapter(
            itemBindingInflater = { inflater, parent, attachToParent ->
                ChatAddListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.receiver = item
            },
            onItemClick = { viewModel.addChat(it) }
        )

        binding.receiverRecyclerView.adapter = chatListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}