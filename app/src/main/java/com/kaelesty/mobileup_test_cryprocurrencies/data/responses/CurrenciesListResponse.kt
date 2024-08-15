package com.kaelesty.mobileup_test_cryprocurrencies.data.responses

data class CurrenciesListResponse(
	val currencies: List<CurrencyResponse>
)

data class CurrencyResponse(
	val id: String,
	val symbol: String,
	val name: String,
	val image: String,
	val current_price: Int,
	val price_change_percentage_24h: Float,
)