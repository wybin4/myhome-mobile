package com.example.myhome.presentation.meter.add

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.myhome.common.models.ApartmentGetModel

class ApartmentAdapter(
    context: Context, 
    textViewResourceId: Int, 
    private var values: List<ApartmentGetModel>
) : ArrayAdapter<ApartmentGetModel>(context, textViewResourceId, values) {
    fun updateApartments(apartments: List<ApartmentGetModel>) {
        values = apartments
        notifyDataSetChanged()
    }
    override fun getCount() = values.size
    override fun getItem(position: Int) = values[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = values[position].address
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.text = values[position].address
        return label
    }

}