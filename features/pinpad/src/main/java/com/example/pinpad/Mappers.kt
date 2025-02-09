package com.example.pinpad

import com.example.data.RequestResult
import com.example.paymentapi.model.AmountDTO
import com.example.paymentapi.model.TransactionDTO
import com.example.paymentapi.model.TransactionDetailsDTO
import com.example.pinpad.model.AmountUI
import com.example.pinpad.model.TransactionUI
import com.example.pinpad.pinpad.PurchaseState
import model.TransactionDetailsUI
import java.math.BigDecimal

internal fun RequestResult.toPurchaseState(): PurchaseState {
    return when(this) {
        is RequestResult.InProgress -> PurchaseState.Loading()

        is RequestResult.Error<*> -> {
            val error = this.error
            PurchaseState.Error(error)
        }

        is RequestResult.Success<*> -> {
            val response = this.data as TransactionDTO
            PurchaseState.Success(response.toUi())
        }
    }
}

// TODO: NumberFormatException can be handled later if we don't trust server
internal fun AmountDTO.toUi(): AmountUI =
    AmountUI(
        purchaseAmount = BigDecimal(purchaseAmount),
        currency = currency,
        taxableAmount = BigDecimal(taxableAmount),
        taxRate = BigDecimal(taxRate),
        tipAmount = BigDecimal(tipAmount),
        discountAmount = BigDecimal(discountAmount)
    )

internal fun TransactionDetailsDTO.toUi(): TransactionDetailsUI =
    TransactionDetailsUI(
        timestamp = timestamp
    )

internal fun TransactionDTO.toUi(): TransactionUI =
    TransactionUI(
        transactionId = transactionId,
        status = status,
        amount = amount.toUi(),
        transactionDetails = transactionDetails.toUi()
    )