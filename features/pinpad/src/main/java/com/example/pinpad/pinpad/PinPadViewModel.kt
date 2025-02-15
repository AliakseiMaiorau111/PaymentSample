package com.example.pinpad.pinpad

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

    private val _state: MutableStateFlow<PinPadState> = MutableStateFlow(PinPadState())
    val state = _state.asStateFlow()

    fun valueUpdated(newValue: String) {

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
        performTransaction(_state.value.amountInput)
    }

    fun resetState() {
        _state.value = _state.value.copy(purchaseState = PurchaseState.None())
    }

    private fun performTransaction(enteredAmount: String): Job {
        _state.value = _state.value.copy(purchaseState = PurchaseState.Loading())
        return viewModelScope.launch {
            useCase.get().invoke()
                .map {
                    it.toPurchaseState()
                }.collect {
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