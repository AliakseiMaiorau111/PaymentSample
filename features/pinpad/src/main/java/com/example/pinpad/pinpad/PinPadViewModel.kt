package com.example.pinpad.pinpad

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinpad.toPurchaseState
import com.example.pinpad.usecase.PaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class PinPadViewModel @Inject internal constructor(
    paymentUseCase: Provider<PaymentUseCase>
) : ViewModel() {

    private val useCase = paymentUseCase

//    private val _paymentState: MutableStateFlow<PurchaseState> = MutableStateFlow(PurchaseState.None())
//    val paymentState = _paymentState.asStateFlow()

    private val _state: MutableStateFlow<PinPadState> = MutableStateFlow(PinPadState())
    val state = _state.asStateFlow()

    fun valueUpdated(newValue: String) {
        Log.d("qaz", "VM, value updated: $newValue")
        val valueToUpdate = if (canUpdate(newValue)) {
            newValue
        } else {
            (MAX_AMOUNT * 100).toString()
        }

        _state.value = _state.value.copy(amountInput = valueToUpdate)
    }

    fun digitClick(digit: Int) {
        if (digit < 0 || digit > 9) return

        val newValue = _state.value.amountInput + digit.toString()
        valueUpdated(newValue)
    }

    fun okClick() {
        // Handle performTransaction
        Log.i("qaz", "OkClick handled")
    }

    fun performTransaction(enteredAmount: String): Job {
        return viewModelScope.launch {
            useCase.get().invoke()
                .map {
                    it.toPurchaseState()
                }.collect {
                    //_paymentState.value = it
                    _state.value = _state.value.copy(purchaseState = it)
                }
        }
    }

    private fun canUpdate(value: String): Boolean {
        val cents = value.toCents()
        val maxCents = MAX_AMOUNT * 100

        return cents <= maxCents
    }

    companion object {
        private const val MAX_AMOUNT = 100_000
    }
}

private fun String.toCents(): Int {
    val cents: Int = try {
        toInt()
    } catch (e: NumberFormatException) {
        0
    }

    return cents
}