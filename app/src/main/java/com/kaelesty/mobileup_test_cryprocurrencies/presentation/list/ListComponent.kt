package com.kaelesty.mobileup_test_cryprocurrencies.presentation.list

import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import kotlinx.coroutines.flow.StateFlow

interface ListComponent {

	val model: StateFlow<ListStore.State>

	fun onSwitchPriceType(priceType: PriceType)

	fun onReload() // retry on error screen

	fun onCurrencyClick(currency: Currency)

	fun onPullToRefresh()
}