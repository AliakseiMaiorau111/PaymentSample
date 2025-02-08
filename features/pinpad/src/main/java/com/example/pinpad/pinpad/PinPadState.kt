package com.example.pinpad.pinpad

data class PinPadState(
    val purchaseState: PurchaseState = PurchaseState.None(),
    val amountInput: String? = null
)
