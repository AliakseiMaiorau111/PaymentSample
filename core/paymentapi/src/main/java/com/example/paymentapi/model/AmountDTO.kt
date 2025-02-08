package com.example.paymentapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AmountDTO(
    @SerialName("purchaseAmount") val purchaseAmount: String,
    @SerialName("currency") val currency: String,
    @SerialName("taxableAmount") val taxableAmount: String, // TODO: ? BigDecimal
    @SerialName("taxRate") val taxRate: String,
    @SerialName("tipAmount") val tipAmount: String,
    @SerialName("discountAmount") val discountAmount: String,
)
