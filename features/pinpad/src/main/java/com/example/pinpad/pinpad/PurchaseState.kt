package com.example.pinpad.pinpad

import com.example.pinpad.model.TransactionUI

sealed class PurchaseState(open val transaction: TransactionUI?, open val error: Throwable? = null) {

    class None(): PurchaseState(transaction = null, error = null)

    class Loading(): PurchaseState(transaction = null, error = null)

    class Error(override val error: Throwable) : PurchaseState(transaction = null, error = error)

    class Success(override val transaction: TransactionUI) : PurchaseState(transaction = transaction, error = null)
}