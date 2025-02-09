package com.example.pinpad.model

import java.math.BigDecimal

data class AmountUI(
    val purchaseAmount: BigDecimal,
    val currency: String,
    val taxableAmount: BigDecimal,
    val taxRate: BigDecimal,
    val tipAmount: BigDecimal,
    val discountAmount: BigDecimal,
)
