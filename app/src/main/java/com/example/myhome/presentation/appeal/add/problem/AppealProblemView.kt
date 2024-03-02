package com.example.myhome.presentation.appeal.add.problem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.presentation.appeal.add.BaseAppealProblemOrClaimView
import com.example.myhome.utils.managers.state.data.DataStateManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppealProblemView : BaseAppealProblemOrClaimView() {
    override val viewModel by viewModels<AppealProblemViewModel>()

    override fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        super.setupDateManager(inflater, container)
        dataStateManager = DataStateManager(
            requireActivity(), dataStateBinding,
            "Обращение добавлено", "Благодарим за обращение! Оно будет обработано в кратчайшие сроки",
            "Ваш запрос на создание обращения в процессе обработки"
        ) {
            findNavController().navigate(R.id.action_appealProblemView_to_appealListView)
        }
    }
}
