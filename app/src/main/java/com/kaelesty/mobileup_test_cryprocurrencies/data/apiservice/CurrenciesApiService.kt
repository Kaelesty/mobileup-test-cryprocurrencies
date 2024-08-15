package com.kaelesty.mobileup_test_cryprocurrencies.data.apiservice

import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrenciesListResponse
import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrencyInfoResponse
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType

interface CurrenciesApiService {

	fun getCurrenciesList(priceType: PriceType): CurrenciesListResponse

	fun getCurrencyInfo(id: String): CurrencyInfoResponse
}