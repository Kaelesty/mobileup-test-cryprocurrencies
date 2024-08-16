package com.kaelesty.mobileup_test_cryprocurrencies.presentation.info

import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.list.ListStore
import kotlinx.coroutines.flow.StateFlow

interface InfoComponent {

	val model: StateFlow<InfoStore.State>

	fun navigateBack()

	fun reloadOnError()
}