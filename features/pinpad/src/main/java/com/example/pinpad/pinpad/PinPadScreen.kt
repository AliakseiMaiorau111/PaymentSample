package com.example.pinpad.pinpad

import android.icu.text.DecimalFormat
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ban.currencyamountinput.CurrencyAmountInputVisualTransformation
import com.example.pinpad.R
import kotlin.math.max
import kotlin.math.sin

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
            InitialContent(paddingValues, state.amountInput)
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
    inputValue: String?
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
            CurrencyAmountInput()
        }
    }
}

//private const val MAX_VALUE = 1_000_000
//
//private val symbols = DecimalFormat().decimalFormatSymbols
//private val thousandsSeparator = symbols.groupingSeparator
//private val decimalSeparator = symbols.decimalSeparator

@Composable
private fun CurrencyAmountInput() {
    var value by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier.padding(bottom = 24.dp)
            .width(300.dp),
        value = value,
        textStyle = TextStyle.Default.copy(fontSize = 33.sp, textAlign = TextAlign.Center, fontStyle = FontStyle.Normal),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        onValueChange = { newValue ->
            value = if (newValue.startsWith("0")) {
                ""
            } else {
                newValue
            }
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

//class ThousandSeparatorOffsetMapping(
//    val originalIntegerLength: Int
//) : OffsetMapping {
//
//    override fun originalToTransformed(offset: Int): Int =
//        when (offset) {
//            0, 1, 2 -> 4
//            else -> offset + 1 + calculateThousandsSeparatorCount(originalIntegerLength)
//        }
//
//    override fun transformedToOriginal(offset: Int): Int =
//        originalIntegerLength +
//                calculateThousandsSeparatorCount(originalIntegerLength) +
//                2
//
//    private fun calculateThousandsSeparatorCount(
//        intDigitCount: Int
//    ) = max((intDigitCount - 1) / 3, 0)
//}

//@Composable
//private fun SampleUse() {
//    var value by remember { mutableStateOf("") }
//
//    TextField(
//        value = value,
//        onValueChange = { newValue ->
//            val trimmed = newValue.trimStart('0').trim {
//                !it.isDigit() && it == '.'
//            }
//
//            if (trimmed.isEmpty() || trimmed.toInt() <= MAX_VALUE) {
//                value = trimmed
//            }
//        },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//        keyboardActions = KeyboardActions(
//            onDone = {
//                Log.d("qaz", "On Done")
//            },
//            onNext = {
//                Log.d("qaz", "On Next")
//            },
//            onSend = {
//                Log.d("qaz", "On Send")
//            },
//            onGo = {
//                Log.d("qaz", "On Go")
//            }
//        )
//    )
//}

@Preview(showBackground = true)
@Composable
fun PinPadScreenPreview() {
    Scaffold { padding ->
        InitialContent(
            paddingValues = padding,
            inputValue = ""
        )
    }
}