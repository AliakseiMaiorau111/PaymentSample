package model

data class AmountUI(
    val purchaseAmount: String,
    val currency: String,
    val taxableAmount: String, // TODO: ? BigDecimal
    val taxRate: String,
    val tipAmount: String,
    val discountAmount: String,
)
