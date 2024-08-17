package com.kaelesty.mobileup_test_cryprocurrencies.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesListResponse(
	val currencies: List<CurrencyResponse>
)

@Serializable
data class CurrencyResponse(
	val id: String,
	val symbol: String,
	val name: String,
	val image: String,
	val current_price: Float,
	val price_change_percentage_24h: Float,
)