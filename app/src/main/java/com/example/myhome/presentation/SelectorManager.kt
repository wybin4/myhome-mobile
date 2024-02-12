package com.example.myhome.presentation

import android.R
import android.widget.AutoCompleteTextView
import androidx.fragment.app.FragmentActivity
import com.example.myhome.common.models.Identifiable
import com.google.android.material.textfield.TextInputLayout
import kotlin.reflect.KMutableProperty0

class SelectorManager<T : Identifiable>(
    private val fragmentActivity: FragmentActivity,
    private val selectorList: AutoCompleteTextView,
    private val selector: TextInputLayout,
    private val validateMessage: String,
    private var selectedId: KMutableProperty0<Int>
) {
    private lateinit var adapter: SelectorAdapter<T>
    private lateinit var validator: InputValidator

    init {
        setupAdapter()
        setupSelector()
        setupValidator()
    }

    private fun setupSelector() {
        selectorList.setOnItemClickListener { parent, _, position, _ ->
            val selected = parent.getItemAtPosition(position) as? T
            selected?.let {
                selectedId.set(it.id)
                selectorList.setText(it.name, false)
                validator.validate(it.id.toString())
            }
        }
    }

    private fun setupAdapter() {
        adapter = SelectorAdapter(
            fragmentActivity,
            R.layout.simple_spinner_dropdown_item,
            emptyList()
        )
        selectorList.setAdapter(adapter)
    }

    private fun setupValidator() {
        validator = InputValidator(
            selector,
            { text: String? -> text?.isNotBlank() == true },
            "Выберите $validateMessage", null
        )
        selectorList.setAdapter(adapter)
    }

    fun update(items: List<T>) {
        adapter.update(items)
    }

    fun validate(): Boolean {
        val idString = if (selectedId.get() > 0) selectedId.toString() else ""
        return validator.validate(idString)
    }

}
