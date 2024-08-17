package com.kaelesty.mobileup_test_cryprocurrencies.domain.entities

enum class PriceType (
	val title: String, // usd, rub
	val symbol: Char // $
){
	USD("usd", '$'),
	RUB("rub", '₽')
}