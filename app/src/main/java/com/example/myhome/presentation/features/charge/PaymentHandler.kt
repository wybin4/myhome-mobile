package com.example.myhome.presentation.features.charge

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity

class PaymentHandler(private val context: FragmentActivity) {
    companion object {
        private const val BASE_URL = "https://yoomoney.ru/quickpay/confirm"
    }

    fun handlePayment(newValue: Double, spdId: Int, checkingAccount: String) {
        val queryString = "receiver=$checkingAccount&label=$spdId&quickpay-form=button&sum=$newValue"
        val url = "$BASE_URL?$queryString"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}
