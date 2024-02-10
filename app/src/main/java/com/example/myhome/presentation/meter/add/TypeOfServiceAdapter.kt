package com.example.myhome.presentation.meter.add

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.myhome.common.models.TypeOfServiceGetModel

class TypeOfServiceAdapter(
    context: Context, 
    textViewResourceId: Int, 
    private var values: List<TypeOfServiceGetModel>
) : ArrayAdapter<TypeOfServiceGetModel>(context, textViewResourceId, values) {
    fun updateTypeOfServices(typesOfService: List<TypeOfServiceGetModel>) {
        values = typesOfService
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