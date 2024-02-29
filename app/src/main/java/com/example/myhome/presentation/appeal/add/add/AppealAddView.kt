package com.example.myhome.presentation.appeal.add.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.AppealAddViewBinding
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

    override fun showSuccessState() {
        findNavController().navigate(R.id.action_appealAddView_to_appealListView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _layoutBinding = null
    }

}
