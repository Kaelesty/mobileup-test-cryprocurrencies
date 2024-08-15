package com.kaelesty.mobileup_test_cryprocurrencies.domain.entities

data class Currency(
	val apiId: String, // bitcoin (id to request additional data)
	val name: String, // Bitcoin
	val shortName: String, // BTC
	val imageUrl: String,
	val priceType: PriceType,
	val price: Float,
	val changePercentage: Float,
)