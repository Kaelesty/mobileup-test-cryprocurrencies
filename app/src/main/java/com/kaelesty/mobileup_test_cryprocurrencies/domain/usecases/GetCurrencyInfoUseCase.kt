package com.kaelesty.mobileup_test_cryprocurrencies.domain.usecases

import com.kaelesty.mobileup_test_cryprocurrencies.domain.repositories.CurrenciesRepository
import javax.inject.Inject

class GetCurrencyInfoUseCase @Inject constructor(
	private val repository: CurrenciesRepository
) {

	suspend operator fun invoke(id: String) = repository.getCurrencyInfo(id)
}