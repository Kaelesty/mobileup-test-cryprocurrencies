package com.kaelesty.mobileup_test_cryprocurrencies.data.mappers

import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrencyInfoResponse
import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrencyResponse
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.CurrencyInfo
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import javax.inject.Inject

class CurrenciesMapper @Inject constructor() {

	fun mapCurrencyResponseToDomain(response: CurrencyResponse, priceType: PriceType) = Currency(
		meta = Currency.Meta(
			apiId = response.id,
			shortName = response.symbol,
			imageUrl = response.image,
			name = response.name,
		),
		pricing = Currency.Pricing(
			price = response.current_price,
			changePercentage = response.price_change_percentage_24h,
			priceType = priceType
		)
	)

	fun mapCurrencyInfoResponseToDomain(response: CurrencyInfoResponse) = CurrencyInfo(
		description = response.description.en,
		categories = response.categories
	)
}