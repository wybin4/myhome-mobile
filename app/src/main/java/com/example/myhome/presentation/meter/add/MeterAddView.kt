package com.example.myhome.presentation.meter.add

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.models.TypeOfServiceGetModel
import com.example.myhome.databinding.MeterAddViewBinding
import com.example.myhome.presentation.CustomDatePicker
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MeterAddView : Fragment() {
    private var _binding: MeterAddViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterAddViewModel>()
    private lateinit var apartmentAdapter: ApartmentAdapter
    private lateinit var typeOfServiceAdapter: TypeOfServiceAdapter
    private lateinit var verifiedAtPicker: CustomDatePicker
    private lateinit var issuedAtPicker: CustomDatePicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MeterAddViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupAdapters()
        verifiedAtPicker = CustomDatePicker(requireActivity(), binding.verifiedAt)
        binding.verifiedAt.setOnClickListener {
            verifiedAtPicker.show()
        }
        issuedAtPicker = CustomDatePicker(requireActivity(), binding.issuedAt)
        binding.issuedAt.setOnClickListener {
            issuedAtPicker.show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apartmentList.observe(viewLifecycleOwner) { apartments ->
            apartmentAdapter.updateApartments(apartments)
        }
        viewModel.typeOfServiceList.observe(viewLifecycleOwner) { typesOfService ->
            Log.e("typeOfServiceList", typesOfService.toString())
            typeOfServiceAdapter.updateTypeOfServices(typesOfService)
        }
    }

    private fun setupAdapters() {
        apartmentAdapter = ApartmentAdapter(
            requireActivity(),
            R.layout.simple_spinner_dropdown_item,
            emptyList()
        )
        binding.apartmentList.setAdapter(apartmentAdapter)
        binding.apartmentList.setOnItemClickListener { parent, _, position, id ->
            val selectedApartment = parent.getItemAtPosition(position) as ApartmentGetModel
            binding.apartmentList.setText(selectedApartment.address, false)
            // selectedApartment.id
        }

        typeOfServiceAdapter = TypeOfServiceAdapter(
            requireActivity(),
            R.layout.simple_spinner_dropdown_item,
            emptyList()
        )
        binding.typeOfServiceList.setAdapter(typeOfServiceAdapter)
        binding.typeOfServiceList.setOnItemClickListener { parent, _, position, id ->
            val selectedTypeOfService = parent.getItemAtPosition(position) as TypeOfServiceGetModel
            binding.typeOfServiceList.setText(selectedTypeOfService.name, false)
            // selectedTypeOfService.id
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
