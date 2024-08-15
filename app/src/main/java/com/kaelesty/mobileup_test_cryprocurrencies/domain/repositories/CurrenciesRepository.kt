package com.kaelesty.mobileup_test_cryprocurrencies.domain.repositories

import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.CurrencyInfo

interface CurrenciesRepository {

	suspend fun getCurrenciesList(): List<Currency>

	suspend fun getCurrencyInfo(id: String): CurrencyInfo
}