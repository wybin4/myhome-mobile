package com.example.myhome.presentation.appeal.add.problem

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.presentation.appeal.add.BaseAppealProblemOrClaimView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppealProblemView : BaseAppealProblemOrClaimView() {
    override val viewModel by viewModels<AppealProblemViewModel>()
    override fun showSuccessState() {
        findNavController().navigate(R.id.action_appealProblemView_to_appealListView)
    }
}
