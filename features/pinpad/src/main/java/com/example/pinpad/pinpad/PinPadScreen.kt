package com.example.pinpad.pinpad

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ban.currencyamountinput.CurrencyAmountInputVisualTransformation
import com.example.pinpad.R

@Composable
fun PinPadScreen(
    paddingValues: PaddingValues,
    navigateToReceipt: (String) -> Unit,
    viewModel: PinPadViewModel
) {

    //val state by viewModel.paymentState.collectAsState()
    val state by viewModel.state.collectAsState()

    when (state.purchaseState) {
        is PurchaseState.None -> {
            InitialContent(
                paddingValues = paddingValues,
                inputValue = state.amountInput,
                okClicked = {
                    viewModel.okClick()
                },
                updateInput = { updatedValue ->
                    viewModel.valueUpdated(updatedValue)

                }
            ) { digit ->
                viewModel.digitClick(digit)
            }
        }

        is PurchaseState.Loading -> {

        }

        is PurchaseState.Error -> {

        }

        is PurchaseState.Success -> {

        }
    }
//    Column {
//        Button(modifier = Modifier.background(Color.LightGray),
//            onClick = {
//                //viewModel.performTransaction("")
//            }) {
//            when (state.purchaseState) {
//                is PurchaseState.Loading -> {
//                    Text("Loading")
//                }
//
//                is PurchaseState.Error -> {
//                    Text("Error")
//                }
//
//                is PurchaseState.Success -> {
//                    //Text("Success: ${state.transaction?.amount?.purchaseAmount}")
//                    Text("Success: ${state.purchaseState.transaction?.amount?.purchaseAmount}")
//                }
//
//                else -> {
//                    // Text("None")
//
//                    //SampleUse()
//                    //CurrencyAmountInput()
//                }
//            }
//        }
//    }

}

@Composable
private fun InitialContent(
    paddingValues: PaddingValues,
    inputValue: String,
    okClicked: () -> Unit,
    updateInput: (String) -> Unit,
    digitClicked: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .padding(top = 38.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                fontSize = 33.sp,
                color = Color(0xFF363636),
                text = stringResource(R.string.pinpad_purchase)
            )
        }

        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color(0xFF666666),
                text = stringResource(R.string.pinpad_please_enter_amount)
            )
        }

        Box(
            modifier = Modifier
                .padding(top = 28.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CurrencyAmountInput(
                value = inputValue,
                updateInput = updateInput
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp)
                .background(Color(0xFFF6F6F6))
        ) {
            for (i in 1..3) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (j in 1..3) {
                        val digit = (i - 1) * 3 + j
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.Green)
                        ) {
                            DigitButton(digit, digitClicked)
                        }
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Green)
                ) {
                    OtherButton(
                        caption = " ",
                        digitClicked = { },
                        okClicked = { })
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Green)
                ) {
                    OtherButton(caption = "0",
                        digitClicked = digitClicked,
                        okClicked = okClicked)
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Green)
                ) {
                    OtherButton(
                        caption = stringResource(R.string.pinpad_ok),
                        digitClicked = digitClicked,
                        okClicked = okClicked,
                        isHighlighted = true
                    )
                }
            }
        }
    }
}

@Composable
private fun CurrencyAmountInput(
    value: String,
    updateInput: (String) -> Unit
) {
    //var value by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier
            .padding(bottom = 24.dp)
            .width(300.dp),
        value = value,
        textStyle = TextStyle.Default.copy(
            fontSize = 33.sp,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Normal
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        onValueChange = { newValue ->
            val updatedValue = if (newValue.startsWith("0")) {
                ""
            } else {
                newValue
            }

            updateInput(updatedValue)
        },
        visualTransformation = CurrencyAmountInputVisualTransformation(
            fixedCursorAtTheEnd = true
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        keyboardActions = KeyboardActions(
            onDone = {
                Log.d("qaz", "On Done")
            },
            onNext = {
                Log.d("qaz", "On Next")
            },
            onSend = {
                Log.d("qaz", "On Send")
            },
            onGo = {
                Log.d("qaz", "On Go")
            }
        )
    )
}

@Composable
fun DigitButton(
    digit: Int,
    digitClicked: (Int) -> Unit
) {
    Button(
        onClick = {
            digitClicked(digit)
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF6F6F6))
    ) {
        Text(
            text = digit.toString(),
            fontSize = 33.sp
        )
    }
}

@Composable
fun OtherButton(
    caption: String,
    digitClicked: (Int) -> Unit,
    okClicked: () -> Unit,
    isHighlighted: Boolean = false
) {
    Button(
        onClick = {
            if (isHighlighted) {
                okClicked()
            } else if (caption == "0") {
                digitClicked(0)
            }

        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor =
            if (isHighlighted) Color(0xFF0DA69C) else Color(0xFFF6F6F6)
        )
    ) {
        Text(
            text = caption,
            fontSize = 33.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PinPadScreenPreview() {
    Scaffold { padding ->
        InitialContent(
            paddingValues = padding,
            inputValue = "0",
            {},
            {},
            {}
        )
    }
}