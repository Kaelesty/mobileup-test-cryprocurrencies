package com.kaelesty.mobileup_test_cryprocurrencies.data.repositories

import com.kaelesty.mobileup_test_cryprocurrencies.data.apiservice.CurrenciesApiService
import com.kaelesty.mobileup_test_cryprocurrencies.data.mappers.CurrenciesMapper
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.CurrencyInfo
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import com.kaelesty.mobileup_test_cryprocurrencies.domain.repositories.CurrenciesRepository
import javax.inject.Inject

class TestCurrenciesRepositoryImpl @Inject constructor(
	private val apiService: CurrenciesApiService,
	private val currenciesMapper: CurrenciesMapper,
): CurrenciesRepository {

	override suspend fun getCurrenciesList(priceType: PriceType) = listOf(
		Currency("bitcoin", "Bitcoin", "BTC",
			"", PriceType.USD, 4444.2f, -4.2f),
		Currency("ethereum", "Ethereum", "ETH",
			"", PriceType.USD, 1454.6f, +1.1f)
	)


	override suspend fun getCurrencyInfo(id: String) = CurrencyInfo(
		"Famous CryptoCurrency", listOf("Currency", "Crypto")
	)

}