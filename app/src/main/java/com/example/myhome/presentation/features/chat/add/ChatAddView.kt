package com.example.myhome.presentation.features.chat.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.ChatAddListItemBinding
import com.example.myhome.databinding.ChatAddListItemLoadingBinding
import com.example.myhome.databinding.ChatAddListViewBinding
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.ConstantsUi
import com.example.myhome.presentation.features.chat.ChatAddStateManager
import com.example.myhome.presentation.features.chat.models.ReceiverUiModel
import com.example.myhome.presentation.state.list.ListState
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

    private lateinit var dataAddStateManager: ChatAddStateManager
    private lateinit var dataAddStateBinding: DataStateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatAddListViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupInfiniteRecyclerView()
        setupDateManager(inflater, container)

        binding.receiverSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nameFilter = s?.toString() ?: ""
                viewModel.filterReceiverListByName(nameFilter)
            }

            override fun afterTextChanged(s: Editable?) { }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeList()
        observeResourceState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchReceiverList()
    }

    private fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        dataAddStateBinding = DataStateBinding.inflate(inflater, container, false)
        dataAddStateManager = ChatAddStateManager(
            requireActivity(), dataAddStateBinding,
            "Ваш запрос на создание чата в процессе обработки"
        ) { }
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
            findNavController().navigate(R.id.action_chatAddView_to_chatGetView, bundle)
        }
    }

    private fun observeResourceState() {
        viewModel.receiverListState.observe(viewLifecycleOwner) {
            updateViewState(it)
        }
        viewModel.chatAddState.observe(viewLifecycleOwner) { resource ->
            dataAddStateManager.observeState(resource)
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
        binding.receiverRecyclerView.itemAnimator = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}