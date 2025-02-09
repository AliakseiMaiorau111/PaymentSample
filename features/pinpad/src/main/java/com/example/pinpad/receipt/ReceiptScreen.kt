package com.example.pinpad.receipt

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pinpad.R
import com.example.pinpad.model.TransactionUI
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.text.NumberFormat
import java.util.Locale

private val formatter: NumberFormat = NumberFormat.getNumberInstance(Locale.GERMANY).also {
    it.minimumFractionDigits = 2
    it.maximumFractionDigits = 2
}

@Composable
fun ReceiptScreen(
    transactionInfo: String,
    viewModel: ReceiptViewModel
) {

    val transaction = try {
        Gson().fromJson(transactionInfo, TransactionUI::class.java)
    } catch (e: JsonSyntaxException) {
        null
    }

    val state by viewModel.state.collectAsState()

    if (transaction == null) {
        ErrorContent()
    } else if (state.isBlank()) {
        // Just in case better to check for infinite loop if it is blank so far  after calculations
        viewModel.calculateReceipt(transaction)
        LoadingContent()
    } else {
        ReceiptContent(transaction, state)
    }
}

@Composable
fun ReceiptContent(
    transactionUI: TransactionUI,
    calculatedState: ReceiptState
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(2f)) {
                Text(stringResource(R.string.receipt_transaction_id), fontSize = 14.sp)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(transactionUI.transactionId, fontSize = 14.sp, color = Color.Red)
            }
            Column(modifier = Modifier.weight(2f)) {
                Text(stringResource(R.string.receipt_transaction_status), fontSize = 14.sp)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(transactionUI.status, fontSize = 14.sp, color = Color.Red)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        val finalAmount = calculatedState.finalAmount
        val tax = calculatedState.tax

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(2f)) {
                Text(stringResource(R.string.receipt_final_amount), fontSize = 14.sp)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(formatter.format(finalAmount), fontSize = 14.sp, color = Color.Red)
            }
            Column(modifier = Modifier.weight(2f)) {
                Text(stringResource(R.string.receipt_tax), fontSize = 14.sp)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(formatter.format(tax), fontSize = 14.sp, color = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val dateTime = calculatedState.timeText

        Row(modifier = Modifier.fillMaxWidth()) {
            MultiColorText(
                text1 = stringResource(R.string.receipt_date),
                text2 = "  $dateTime",
                color2 = Color.Blue
            )
        }
    }
}

@Composable
private fun MultiColorText(
    text1: String,
    color1: Color = Color.Unspecified,
    text2: String,
    color2: Color,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(color = color1, fontSize = fontSize)) {
            append(text1)
        }
        withStyle(style = SpanStyle(color = color2, fontSize = fontSize)) {
            append(text2)
        }
    })
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = Color.Green
        )
    }
}

@Composable
fun ErrorContent() {
    // Not implemented for this case
}