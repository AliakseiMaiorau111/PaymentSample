package com.example.pinpad.receipt

import java.math.BigDecimal

data class ReceiptState(
    val finalAmount: BigDecimal = BigDecimal.ZERO,
    val tax: BigDecimal = BigDecimal.ZERO,
    val timeText: String = ""
)

fun ReceiptState.isBlank(): Boolean =
    finalAmount == BigDecimal.ZERO && tax == BigDecimal.ZERO && timeText.isBlank()
