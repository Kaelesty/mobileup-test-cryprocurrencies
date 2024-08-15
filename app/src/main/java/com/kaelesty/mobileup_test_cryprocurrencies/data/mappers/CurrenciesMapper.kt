package com.kaelesty.mobileup_test_cryprocurrencies.data.mappers

import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrencyInfoResponse
import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrencyResponse
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.CurrencyInfo
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType

object CurrenciesMapper {

	fun mapCurrency_ResponseToDomain(response: CurrencyResponse, priceType: PriceType) = Currency(
		apiId = response.id,
		shortName = response.symbol,
		imageUrl = response.image,
		name = response.name,
		price = response.current_price.toFloat(),
		changePercentage = response.price_change_percentage_24h,
		priceType = priceType
	)

	fun mapCurrencyInfo_ResponseToDomain(response: CurrencyInfoResponse) = CurrencyInfo(
		description = response.description.en,
		categories = response.categories
	)
}