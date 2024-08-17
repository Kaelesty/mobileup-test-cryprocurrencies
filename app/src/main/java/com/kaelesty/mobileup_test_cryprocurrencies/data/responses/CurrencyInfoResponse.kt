package com.kaelesty.mobileup_test_cryprocurrencies.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyInfoResponse(
	val categories: List<String>,
	val description: Description
)

@Serializable
data class Description(
	val en: String
)