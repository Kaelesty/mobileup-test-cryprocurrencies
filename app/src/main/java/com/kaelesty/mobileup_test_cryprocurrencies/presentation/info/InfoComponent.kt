package com.kaelesty.mobileup_test_cryprocurrencies.presentation.info

import kotlinx.coroutines.flow.StateFlow

interface InfoComponent {

	val model: StateFlow<InfoStore.State>

	fun navigateBack()

	fun reloadOnError()

	fun openURL(url: String)
}