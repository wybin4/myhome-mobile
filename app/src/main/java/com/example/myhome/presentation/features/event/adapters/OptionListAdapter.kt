package com.example.myhome.presentation.features.event.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.OptionItemBinding
import com.example.myhome.presentation.features.event.OptionUiManager
import com.example.myhome.presentation.features.event.OptionUiModel
import com.example.myhome.presentation.utils.pickers.ColorPicker

class OptionListAdapter(
    context: FragmentActivity,
    private val options: List<Pair<OptionUiModel, OptionItemBinding>>,
    private val isVotingClosed: Boolean,
    isSomeSelected: Boolean,
    private val onItemClick: ((OptionUiModel, List<Pair<OptionUiModel, OptionItemBinding>>) -> Unit)
) : ArrayAdapter<OptionUiModel>(context, 0, options.map { it.first }), ColorPicker {
    private val optionUiManager = OptionUiManager(context, isVotingClosed, isSomeSelected)

    private fun forEachOption(action: (OptionUiModel, OptionItemBinding) -> Unit) {
        options.forEach { (option, binding) ->
            action(option, binding)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val option = getItem(position)
        if (option != null) {
            val binding = options[position].second

            optionUiManager.toggle(binding, option)
            setOnClickListener(binding, option)

            binding.executePendingBindings()
            return binding.root
        }
        throw IllegalArgumentException("Unknown binding type")
    }

    private fun setOnClickListener(binding: OptionItemBinding, option: OptionUiModel) {
        if (!isVotingClosed && !optionUiManager.isSomeSelected) {
            binding.root.setOnClickListener {
                onItemClick(option, options)

                option.selected = true
                optionUiManager.isSomeSelected = true

                forEachOption { option, viewDataBinding ->
                    optionUiManager.toggle(viewDataBinding, option)
                    viewDataBinding.root.setOnClickListener(null)
                }
            }
        }
    }

}