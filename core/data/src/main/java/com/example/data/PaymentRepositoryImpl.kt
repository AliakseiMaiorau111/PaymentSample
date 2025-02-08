package com.example.data

import android.util.Log
import com.example.paymentapi.PaymentApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: PaymentApi
) : PaymentRepository {

    override fun performTransaction(): Flow<RequestResult> {
        val apiRequest = flow {
            val paymentResult = api.getTransaction()
            emit(paymentResult)
        }.onEach { result ->
            if (result.isFailure) {
                Log.e("qaz", "Error in performTransaction, Cause = ${result.exceptionOrNull()}")
            }
        }.map {
            it.toRequestResult()
        }

        return apiRequest
    }
}