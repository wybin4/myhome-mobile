package com.example.myhome.features.charge

import android.util.Log
import com.example.myhome.ConstantsData.Companion.CHARGE_PAGE_SIZE
import com.example.myhome.features.CustomPagingSource
import com.example.myhome.features.charge.dtos.ChargeListItemResponse
import com.example.myhome.features.charge.dtos.ChargeListRequest
import com.example.myhome.models.MetaRequest

class ChargePagingSource(private val chargeApiService: ChargeApiService) :
    CustomPagingSource<ChargeListItemResponse>() {
    override suspend fun invoke(page: Int): List<ChargeListItemResponse> {
        val response = chargeApiService.listCharge(
            ChargeListRequest(
                userId = 1,
                meta = MetaRequest(page, CHARGE_PAGE_SIZE)
            )
        )
        Log.e("ChargePagingSource", response.toString())
        return response.charges
    }

}