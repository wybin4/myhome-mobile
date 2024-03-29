package com.example.myhome.features.charge

import com.example.myhome.features.CustomPagingSource
import com.example.myhome.features.charge.dtos.ChargeListItemResponse
import com.example.myhome.features.charge.dtos.ChargeListRequest
import com.example.myhome.models.MetaRequest

class ChargePagingSource(private val chargeApiService: ChargeApiService) :
    CustomPagingSource<ChargeListItemResponse>() {
    companion object {
        const val CHARGE_PAGE_SIZE = 6
    }

    override suspend fun invoke(page: Int): List<ChargeListItemResponse> {
        return chargeApiService.listCharge(
            ChargeListRequest(
                userId = 1,
                meta = MetaRequest(page, CHARGE_PAGE_SIZE)
            )
        )
    }

}