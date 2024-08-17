package com.kaelesty.mobileup_test_cryprocurrencies.data.repositories

import com.kaelesty.mobileup_test_cryprocurrencies.data.apiservice.CurrenciesApiService
import com.kaelesty.mobileup_test_cryprocurrencies.data.mappers.CurrenciesMapper
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import com.kaelesty.mobileup_test_cryprocurrencies.domain.repositories.CurrenciesRepository
import javax.inject.Inject

class CurrenciesRepositoryImpl @Inject constructor(
	private val apiService: CurrenciesApiService,
	private val currenciesMapper: CurrenciesMapper,
): CurrenciesRepository {

	override suspend fun getCurrenciesList(priceType: PriceType, count: Int): List<Currency>
		= apiService.getCurrenciesList(priceType, count)
			.map { currencyResponse ->
				currenciesMapper.mapCurrency_ResponseToDomain(currencyResponse, priceType)
			}


	override suspend fun getCurrencyInfo(id: String) = apiService.getCurrencyInfo(id).let {
		currenciesMapper.mapCurrencyInfo_ResponseToDomain(it)
	}

}