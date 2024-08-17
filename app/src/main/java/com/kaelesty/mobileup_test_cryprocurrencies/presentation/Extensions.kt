package com.kaelesty.mobileup_test_cryprocurrencies.presentation

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


fun Float.asPrice(): String = DecimalFormat(
	"#,##0.00",
	DecimalFormatSymbols(Locale.US)
).format(this)

fun Float.asChangePercentage() = "%.2f".format(this)