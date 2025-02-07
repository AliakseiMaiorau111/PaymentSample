package com.example.paymentapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDTO(
    @SerialName("transactionId") val transactionId: String,
    @SerialName("status") val status: String,
    @SerialName("amount") val amount: AmountDTO,
    @SerialName("transactionDetails") val transactionDetails: TransactionDetailsDTO,

)
