package com.kaelesty.mobileup_test_cryprocurrencies.data.apiservice

import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrencyInfoResponse
import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrencyResponse
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType

interface CurrenciesApiService {

	suspend fun getCurrenciesList(priceType: PriceType, count: Int): List<CurrencyResponse>

	suspend fun getCurrencyInfo(id: String): CurrencyInfoResponse
}