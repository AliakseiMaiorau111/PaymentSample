package com.example.paymentapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDetailsDTO(
    @SerialName("timestamp") val timestamp: String,
)
