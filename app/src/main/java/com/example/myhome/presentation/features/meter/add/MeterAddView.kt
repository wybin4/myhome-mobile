package com.example.myhome.presentation.features.meter.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myhome.databinding.MeterAddViewBinding
import com.example.myhome.presentation.features.appeal.add.add.BaseAppealAddView
import com.example.myhome.presentation.state.DataStateManager
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

    override fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        super.setupDateManager(inflater, container)
        dataStateManager = DataStateManager(
            requireActivity(), dataStateBinding,
            "Обращение добавлено", "Благодарим за обращение! Ваш запрос на добавление счетчика будет обработан в кратчайшие сроки.",
            "Ваш запрос на добавление счетчика находится в процессе обработки"

        ) {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _layoutBinding = null
    }

}
