package com.example.myhome.presentation.meter.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.ApartmentListItemBinding
import com.example.myhome.databinding.MeterListItemBinding
import com.example.myhome.databinding.MeterListViewBinding
import com.example.myhome.utils.adapters.CustomListAdapter
import com.example.myhome.utils.models.ApartmentUiModel
import com.example.myhome.utils.models.MeterUiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterListView : Fragment() {
    private var _binding: MeterListViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterListViewModel>()
    private lateinit var meterListAdapter: CustomListAdapter<MeterUiModel, MeterListItemBinding>
    private lateinit var apartmentListAdapter: CustomListAdapter<ApartmentUiModel, ApartmentListItemBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MeterListViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupCarouselView()

        binding.addMeterButton.setOnClickListener {
            findNavController().navigate(R.id.action_MeterListView_to_MeterAddView)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.meterList.observe(viewLifecycleOwner) {
            meterListAdapter.submitList(it)
        }
        viewModel.apartmentList.observe(viewLifecycleOwner) {
            apartmentListAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        meterListAdapter = CustomListAdapter(
            context = requireActivity(),
            itemBindingInflater = { inflater, parent, attachToParent ->
                MeterListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.meter = item
            },
            onItemClick = { item ->
                val bundle = viewModel.mapMeterUiToGetParcel(item).toBundle()
                findNavController().navigate(R.id.action_MeterListView_to_MeterGetView, bundle)
            }
        )

        binding.meterRecyclerView.adapter = meterListAdapter
    }

    private fun setupCarouselView() {
        apartmentListAdapter = CustomListAdapter(
            context = requireActivity(),
            itemBindingInflater = { inflater, parent, attachToParent ->
                ApartmentListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.apartment = item
            },
            onItemClick = { item ->
                viewModel.changeSelectedApartment(item)
                apartmentListAdapter.notifyDataSetChanged()
            }
        )

        binding.apartmentRecyclerView.adapter = apartmentListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
