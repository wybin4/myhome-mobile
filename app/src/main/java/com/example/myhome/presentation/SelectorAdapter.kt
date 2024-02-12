package com.example.myhome.presentation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.myhome.common.models.Identifiable

class SelectorAdapter<T : Identifiable>(
    context: Context,
    textViewResourceId: Int,
    private var values: List<T>
): ArrayAdapter<T>(context, textViewResourceId, values) {
    fun update(items: List<T>) {
        values = items
        notifyDataSetChanged()
    }
    override fun getCount() = values.size
    override fun getItem(position: Int) = values[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = values[position].name
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.text = values[position].name
        return label
    }

}