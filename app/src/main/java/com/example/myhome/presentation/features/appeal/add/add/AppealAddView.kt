package com.example.myhome.presentation.features.appeal.add.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.AppealAddViewBinding
import com.example.myhome.presentation.state.DataStateManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppealAddView : BaseAppealAddView() {
    private var _layoutBinding: AppealAddViewBinding? = null
    private val layoutBinding get() = _layoutBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _layoutBinding = AppealAddViewBinding.inflate(inflater, container, false)
        layoutBinding.lifecycleOwner = this
        layoutBinding.viewModel = viewModel

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
            "Обращение добавлено", "Благодарим за обращение! Оно будет обработано в кратчайшие сроки",
            "Ваш запрос на создание обращения в процессе обработки"
        ) {
            findNavController().navigate(R.id.action_appealAddView_to_appealListView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _layoutBinding = null
    }

}
