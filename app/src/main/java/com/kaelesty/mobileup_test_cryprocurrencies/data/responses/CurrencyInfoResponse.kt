package com.kaelesty.mobileup_test_cryprocurrencies.data.responses

data class CurrencyInfoResponse(
	val categories: List<String>,
	val description: Description
)

data class Description(
	val en: String
)