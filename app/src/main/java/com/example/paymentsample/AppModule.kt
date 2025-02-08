package com.example.paymentsample

import com.example.data.PaymentRepository
import com.example.data.PaymentRepositoryImpl
import com.example.paymentapi.PaymentApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val CONNECT_TIMEOUT_SECONDS = 10L
    private const val READ_TIMEOUT_SECONDS = 60L

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRapidApi(okHttpClient: OkHttpClient?): PaymentApi {
        return PaymentApi(
            baseUrl = BuildConfig.PAYMENT_API_BASE_URL,
            okHttpClient = okHttpClient
        )
    }

    @Provides
    @Singleton
    fun providePaymentRepository(paymentApi: PaymentApi): PaymentRepository {
        return PaymentRepositoryImpl(paymentApi)
    }
}