package com.example.pinpad

import app.cash.turbine.test
import com.example.data.RequestResult
import com.example.paymentapi.model.AmountDTO
import com.example.paymentapi.model.TransactionDTO
import com.example.paymentapi.model.TransactionDetailsDTO
import com.example.pinpad.pinpad.PinPadViewModel
import com.example.pinpad.pinpad.PurchaseState
import com.example.pinpad.usecase.PaymentUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Provider

@RunWith(MockitoJUnitRunner::class)
class CommonTest {

    private val paymentUseCase: Provider<PaymentUseCase> = mockk()
    private val paymentUseCaseMock: PaymentUseCase = mockk()
    private lateinit var pinPadViewModel: PinPadViewModel

    @Before
    fun setup() {
        every { paymentUseCase.get() } returns paymentUseCaseMock
    }

    @Test
    fun `payment request emits loading and then success`() = runTest {
        val transactionResponse = TransactionDTO(
            transactionId = "12345",
            status = "Success",
            amount = AmountDTO(
                purchaseAmount = "10.00",
                currency = "USD",
                taxableAmount = "9.0",
                taxRate = "0.10",
                tipAmount = "1.0",
                discountAmount = "0.50"
            ),
            transactionDetails = TransactionDetailsDTO(
                timestamp = "2025-02-09T12:30:00Z"
            )
        )

        val flow = flowOf(RequestResult.Success(data = transactionResponse))
        every { paymentUseCaseMock.invoke() } returns flow

        pinPadViewModel = PinPadViewModel(paymentUseCase)

        pinPadViewModel.state.test {
            pinPadViewModel.okClick()

            val noneItem = awaitItem()
            assert(noneItem.purchaseState is PurchaseState.None) {
                "State is expected to be None, but actual: ${noneItem.purchaseState}"
            }

            val loadingItem = awaitItem()
            assert(loadingItem.purchaseState is PurchaseState.Loading) {
                "State is expected to be Loading, but actual: ${loadingItem.purchaseState}"
            }

            val successItem = awaitItem()
            assert(successItem.purchaseState is PurchaseState.Success) {
                "State is expected to be Success, but actual: ${successItem.purchaseState}"
            }

            val successState = successItem.purchaseState as PurchaseState.Success
            val expectedData = transactionResponse.toUi()
            val actualData = successState.transaction

            assert(expectedData == actualData) {
                "The response data is wrong"
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `payment request emits loading and then error`() = runTest {
        val flow = flowOf(
            RequestResult.Error(error = Throwable("Error message"))
        )

        every { paymentUseCaseMock.invoke() } returns flow

        pinPadViewModel = PinPadViewModel(paymentUseCase)

        pinPadViewModel.state.test {
            pinPadViewModel.okClick()

            val noneItem = awaitItem()
            assert(noneItem.purchaseState is PurchaseState.None) {
                "State is expected to be None, but actual: ${noneItem.purchaseState}"
            }

            val loadingItem = awaitItem()
            assert(loadingItem.purchaseState is PurchaseState.Loading) {
                "State is expected to be Loading, but actual: ${loadingItem.purchaseState}"
            }

            val errorItem = awaitItem()
            assert(errorItem.purchaseState is PurchaseState.Error) {
                "State is expected to be Error, but actual: $errorItem"
            }

            cancelAndIgnoreRemainingEvents()
        }
    }
}