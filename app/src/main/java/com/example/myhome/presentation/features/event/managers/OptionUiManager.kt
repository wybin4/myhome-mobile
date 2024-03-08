package com.example.myhome.presentation.features.event.managers

import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.myhome.R
import com.example.myhome.databinding.OptionItemBinding
import com.example.myhome.presentation.features.event.OptionUiModel
import com.example.myhome.presentation.utils.pickers.ColorPicker

class OptionUiManager(
    private val context: FragmentActivity,
    private val isVotingClosed: Boolean,
    var isSomeSelected: Boolean
): ColorPicker {
    private object ColorConstants {
        val COLOR_CLOSED = R.color.description_normal
        val COLOR_CLOSED_LIGHT_CLICKED = R.color.description_light_clicked
        val COLOR_CLOSED_LIGHT = R.color.description_light
        val COLOR = R.color.primary
        val COLOR_LIGHT_CLICKED = R.color.primary_light_clicked
        val COLOR_LIGHT = R.color.primary_light
    }
    
    private fun resolvePrimaryColor(selected: Boolean): Int {
        return when {
            isVotingClosed && selected -> getColor(context,
                ColorConstants.COLOR_CLOSED
            )
            isVotingClosed && !selected -> getColor(context,
                ColorConstants.COLOR_CLOSED_LIGHT_CLICKED
            )
            !isVotingClosed && selected -> getColor(context, ColorConstants.COLOR)
            else -> getColor(context, ColorConstants.COLOR_LIGHT_CLICKED)
        }
    }

    private fun resolveSecondaryColor(): Int {
        return if (isVotingClosed) {
            getColor(context, ColorConstants.COLOR_CLOSED_LIGHT)
        } else {
            getColor(context, ColorConstants.COLOR_LIGHT)
        }
    }

    private fun View.setVisibility(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun toggle(binding: OptionItemBinding, option: OptionUiModel) {
        val primaryColor = resolvePrimaryColor(option.selected)
        val secondaryColor = resolveSecondaryColor()
        binding.apply {
            resultLayout.setVisibility(option.isResult)

            selectedIndicator.setVisibility(option.selected)
            selectedIndicator.backgroundTintList = ColorStateList.valueOf(secondaryColor)
            selectedIndicator.setWidth(wrapper, option.proportion)

            wrapper.backgroundTintList = ColorStateList.valueOf(secondaryColor)
            circle.backgroundTintList = ColorStateList.valueOf(primaryColor)

            text.setupText(option.text, primaryColor)
            proportion.setupText(option.getFormattedProportion(), primaryColor)
            proportion.setVisibility(isSomeSelected || isVotingClosed)
        }
    }

    private fun FrameLayout.setWidth(parent: FrameLayout, value: Double) {
        parent.post {
            val widthInPixels = (width * value) / 100
            val layoutParams = this.layoutParams as FrameLayout.LayoutParams
            layoutParams.width = widthInPixels.toInt() + 10
            this.layoutParams = layoutParams
        }
    }

    private fun TextView.setupText(textString: String, primaryColor: Int) {
        this.apply {
            text = textString
            setTextColor(primaryColor)
        }
    }
}