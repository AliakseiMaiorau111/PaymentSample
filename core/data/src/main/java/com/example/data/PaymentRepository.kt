package com.example.data

import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun performTransaction(): Flow<RequestResult>
}