package com.example.myhome.utils.listeners

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatAutoCompleteTextView

class SelectorListener : AppCompatAutoCompleteTextView {
    private var indexChangeListener: ((Int) -> Unit)? = null

    fun setIndexChangeListener(listener: (Int) -> Unit) {
        indexChangeListener = listener
    }

    fun notifyIndexChanged(index: Int) {
        indexChangeListener?.invoke(index)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

}