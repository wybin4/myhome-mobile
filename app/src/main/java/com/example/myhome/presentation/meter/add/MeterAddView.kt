package com.example.myhome.presentation.meter.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.MeterAddViewBinding
import com.example.myhome.presentation.appeal.add.add.BaseAppealAddView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterAddView : BaseAppealAddView() {
    private var _layoutBinding: MeterAddViewBinding? = null
    private val layoutBinding get() = _layoutBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _layoutBinding = MeterAddViewBinding.inflate(inflater, container, false)
        layoutBinding.lifecycleOwner = this

        layoutItemsBinding = layoutBinding.layoutItems

        super.onCreateView(inflater, container, savedInstanceState)
        layoutBinding.nextButton.setOnClickListener { nextClick() }

        return layoutBinding.root
    }

    private fun nextClick() {
        if (validate()) {
            imagePicker.checkStoragePermission()
        }
    }

    override fun showSuccessState() {
        findNavController().navigate(R.id.action_meterAddView_to_meterListView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _layoutBinding = null
    }

}
