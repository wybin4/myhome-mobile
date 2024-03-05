package com.example.myhome.presentation.features.appeal.add.claim

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.presentation.features.appeal.add.BaseAppealProblemOrClaimView
import com.example.myhome.presentation.state.data.DataStateManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppealClaimView : BaseAppealProblemOrClaimView() {
    override val viewModel by viewModels<AppealClaimViewModel>()

    override fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        super.setupDateManager(inflater, container)
        dataStateManager = DataStateManager(
            requireActivity(), dataStateBinding,
            "Претензия добавлена", "Благодарим за обращение! Претензия будет обработана в кратчайшие сроки",
            "Ваш запрос на создание обращения в процессе обработки"
        ) {
            findNavController().navigate(R.id.action_appealClaimView_to_appealListView)
        }
    }

}
