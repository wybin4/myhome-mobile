package com.example.myhome.presentation.appeal.add.verify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.databinding.DatePickersViewBinding
import com.example.myhome.utils.pickers.ImagePicker
import com.example.myhome.utils.managers.DatePickersManager
import com.example.myhome.utils.managers.state.data.DataStateManager

abstract class BaseAppealVerifyView : Fragment() {
    protected lateinit var dataStateBinding: DataStateBinding

    protected lateinit var datePickersBinding: DatePickersViewBinding

    protected abstract val viewModel: BaseAppealVerifyViewModel

    lateinit var datePickersManager: DatePickersManager

    lateinit var imagePicker: ImagePicker

    protected lateinit var dataStateManager: DataStateManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupImagePicker()
        setupDatePickers()
        setupDateManager(inflater, container)

        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeResourceState()
    }

    protected open fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        dataStateBinding = DataStateBinding.inflate(inflater, container, false)
    }

    protected open fun setupDatePickers() {
        datePickersManager = DatePickersManager(
            datePickersBinding, requireActivity(),
            viewModel::selectVerifiedAt, viewModel::selectIssuedAt
        )
    }

    protected open fun observeResourceState() {
        viewModel.dataState.observe(viewLifecycleOwner) { resource ->
            dataStateManager.observeGetState(resource)
        }
        viewModel.dataAddState.observe(viewLifecycleOwner) { resource ->
            dataStateManager.observeAddState(resource)
        }
    }

    abstract fun setupImagePicker()

    protected fun validate(): Boolean {
        return datePickersManager.isDatePickersValid()
    }

}
