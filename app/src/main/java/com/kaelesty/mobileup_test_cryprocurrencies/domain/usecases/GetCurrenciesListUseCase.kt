package com.kaelesty.mobileup_test_cryprocurrencies.domain.usecases

import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import com.kaelesty.mobileup_test_cryprocurrencies.domain.repositories.CurrenciesRepository
import javax.inject.Inject

class GetCurrenciesListUseCase @Inject constructor(
	private val repository: CurrenciesRepository
) {

	suspend operator fun invoke(
		priceType: PriceType,
		count: Int = 30
	) = repository.getCurrenciesList(priceType, count)
}