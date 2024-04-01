package com.example.myhome.presentation.features.appeal.add.verify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.databinding.DatePickersViewBinding
import com.example.myhome.presentation.state.DataStateManager
import com.example.myhome.presentation.utils.managers.DatePickersManager
import com.example.myhome.presentation.utils.pickers.ImagePicker

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
        viewModel.getState.observe(viewLifecycleOwner) { state ->
            dataStateManager.observeGetState(state)
        }
        viewModel.addState.observe(viewLifecycleOwner) { state ->
            dataStateManager.observeAddState(state)
        }
    }

    abstract fun setupImagePicker()

    protected fun validate(): Boolean {
        return datePickersManager.isDatePickersValid()
    }

}
