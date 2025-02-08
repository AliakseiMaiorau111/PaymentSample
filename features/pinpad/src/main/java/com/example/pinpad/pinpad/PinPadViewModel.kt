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

//    private val _paymentState: MutableStateFlow<PurchaseState> = MutableStateFlow(PurchaseState.None())
//    val paymentState = _paymentState.asStateFlow()

    private val _state: MutableStateFlow<PinPadState> = MutableStateFlow(PinPadState())
    val state = _state.asStateFlow()

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
}