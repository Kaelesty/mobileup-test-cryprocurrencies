package com.kaelesty.mobileup_test_cryprocurrencies.domain.usecases

import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import com.kaelesty.mobileup_test_cryprocurrencies.domain.repositories.CurrenciesRepository

class GetCurrenciesListUseCase(
	private val repository: CurrenciesRepository
) {

	suspend operator fun invoke(priceType: PriceType) = repository.getCurrenciesList(priceType)
}