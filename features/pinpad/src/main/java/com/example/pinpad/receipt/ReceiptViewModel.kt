package com.example.pinpad.receipt

import androidx.lifecycle.ViewModel
import com.example.pinpad.model.TransactionUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject internal constructor() : ViewModel() {
    private val _state: MutableStateFlow<ReceiptState> = MutableStateFlow(ReceiptState())
    val state = _state.asStateFlow()

    fun calculateReceipt(transactionUI: TransactionUI) {
        val taxableAmount = transactionUI.amount.taxableAmount
        val discount = transactionUI.amount.discountAmount
        val tips = transactionUI.amount.tipAmount
        val finalAmount = taxableAmount - discount + tips

        val tax = finalAmount * transactionUI.amount.taxRate
        val dateTime = dateFormat.parse(transactionUI.transactionDetails.timestamp)!!

        _state.value = ReceiptState(
            finalAmount = finalAmount,
            tax = tax,
            timeText = dateTime.toString()
        )
    }

    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.GERMANY)
    }
}