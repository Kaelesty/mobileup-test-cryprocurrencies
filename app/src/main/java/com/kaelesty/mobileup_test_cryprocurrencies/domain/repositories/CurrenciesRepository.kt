package com.kaelesty.mobileup_test_cryprocurrencies.domain.repositories

import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.CurrencyInfo
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType

interface CurrenciesRepository {

	suspend fun getCurrenciesList(priceType: PriceType, count: Int): List<Currency>

	suspend fun getCurrencyInfo(id: String): CurrencyInfo
}