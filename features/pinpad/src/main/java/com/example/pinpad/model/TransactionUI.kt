package com.example.pinpad.model

import model.TransactionDetailsUI

data class TransactionUI(
    val transactionId: String,
    val status: String,
    val amount: AmountUI,
    val transactionDetails: TransactionDetailsUI,
)
