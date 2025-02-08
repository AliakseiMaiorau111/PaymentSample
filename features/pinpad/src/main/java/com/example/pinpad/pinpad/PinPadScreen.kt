package com.example.pinpad.pinpad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PinPadScreen(
    paddingValues: PaddingValues,
    navigateToReceipt: (String) -> Unit,
    viewModel: PinPadViewModel
) {

    val state by viewModel.paymentState.collectAsState()

    Column {
        Button(modifier = Modifier.background(Color.LightGray),
            onClick = {
                viewModel.performTransaction("")
            }) {
            when(state) {
                is PurchaseState.Loading -> {
                    Text("Loading")
                }
                is PurchaseState.Error -> {
                    Text("Error")
                }
                is PurchaseState.Success -> {
                    Text("Success: ${state.transaction?.amount?.purchaseAmount}")
                }

                else -> {
                    Text("None")
                }
            }
        }
    }

}