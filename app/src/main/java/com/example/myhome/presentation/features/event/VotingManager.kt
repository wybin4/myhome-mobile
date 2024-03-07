package com.example.myhome.presentation.features.event

import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.OptionItemBinding
import com.example.myhome.presentation.features.event.adapters.OptionListAdapter

class VotingManager(
    private val context: FragmentActivity,
    private val onOptionClick: ((OptionUiModel) -> Unit)
) {
    operator fun invoke(voting: VotingUiModel, optionsLayout: LinearLayout) {
        if (voting != null) {
            val isVotingClosed = voting.isClosed()
            val isSomeSelected = voting.isSomeSelected()

            val optionBindings = createOptionBindings(voting, optionsLayout)
            val optionAdapter = createOptionAdapter(context, optionBindings, isVotingClosed, isSomeSelected)

            addOptionsToLayout(voting, optionsLayout, optionAdapter)
        }
    }

    private fun createOptionBindings(voting: VotingUiModel, optionsLayout: LinearLayout): List<Pair<OptionUiModel, OptionItemBinding>> {
        return voting.options.map { option ->
            val binding = OptionItemBinding.inflate(LayoutInflater.from(context), optionsLayout, false)
            Pair(option, binding)
        }
    }

    private fun createOptionAdapter(context: FragmentActivity, optionBindings: List<Pair<OptionUiModel, OptionItemBinding>>, isVotingClosed: Boolean, isSomeSelected: Boolean): OptionListAdapter {
        return OptionListAdapter(context, optionBindings, isVotingClosed, isSomeSelected) { option, options ->
            handleOptionClick(option, options)
        }
    }

    private fun addOptionsToLayout(voting: VotingUiModel, optionsLayout: LinearLayout, optionAdapter: OptionListAdapter) {
        voting.options.forEach { option ->
            val view = optionAdapter.getView(voting.options.indexOf(option), null, optionsLayout)
            optionsLayout.addView(view)
        }
    }

    private fun handleOptionClick(option: OptionUiModel, options: List<Pair<OptionUiModel, OptionItemBinding>>) {
        option.numberOfVotes += 1
        val totalVotes = options.map { it.first }.sumOf { it.numberOfVotes }
        options.forEach { (option, _) ->
            option.calculateProportion(totalVotes)
        }
        onOptionClick(option)
    }
}
