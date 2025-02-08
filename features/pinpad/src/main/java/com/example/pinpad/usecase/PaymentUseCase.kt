package com.example.pinpad.usecase

import com.example.data.PaymentRepository
import com.example.data.RequestResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentUseCase @Inject constructor(
    private val repository: PaymentRepository
) {
    operator fun invoke(): Flow<RequestResult> {
        return repository.performTransaction()
    }
}