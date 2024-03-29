package com.example.myhome.presentation.features.charge.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import com.example.myhome.presentation.models.Adaptive

open class ChargeListAdapter<T: Adaptive, B : ViewDataBinding>(
    context: FragmentActivity,
    private val itemBindingInflater: (LayoutInflater, ViewGroup, Boolean) -> B,
    private val setBinding: (B, T, Int, Int) -> Unit,
    private val layout: LinearLayout
) : ArrayAdapter<T>(context, 0) {
    private var list: MutableList<T> = mutableListOf()

    fun updateList(newList: List<T>) {
        val existingPaymentIds = list.map { it.id }
        val uniqueNewList = newList.filter { !existingPaymentIds.contains(it.id) }
        list.addAll(uniqueNewList)
        uniqueNewList.forEach { newPayment ->
            val view = getView(list.indexOf(newPayment), null, layout)
            layout.addView(view)
        }
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): T? {
        return list.getOrNull(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        if (item != null) {
            val binding = itemBindingInflater(LayoutInflater.from(context), layout, false)
            setBinding(binding, item, position, count)
            binding.executePendingBindings()
            return binding.root
        }
        throw IllegalArgumentException("Unknown binding type")
    }

}

