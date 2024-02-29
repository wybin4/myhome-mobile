package com.example.myhome.presentation.appeal.add.claim

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.presentation.appeal.add.BaseAppealProblemOrClaimView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppealClaimView : BaseAppealProblemOrClaimView() {
    override val viewModel by viewModels<AppealClaimViewModel>()
    override fun showSuccessState() {
        findNavController().navigate(R.id.action_appealClaimView_to_appealListView)
    }

}
