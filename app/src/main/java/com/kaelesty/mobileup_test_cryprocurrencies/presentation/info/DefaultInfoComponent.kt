package com.kaelesty.mobileup_test_cryprocurrencies.presentation.info

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultInfoComponent @Inject constructor(
	componentContext: ComponentContext,
	currencyMeta: Currency.Meta,
	private val storeFactory: InfoStoreFactory,
	private val onNavigateBack: () -> Unit
): InfoComponent, ComponentContext by componentContext {

	private val scope = CoroutineScope(Dispatchers.Main)

	private val store = instanceKeeper.getStore {
		storeFactory.create(currencyMeta)
	}.apply {
		doOnDestroy { scope.cancel() }
		scope.launch {
			labels.collect { label ->
				when(label) {
					InfoStore.Label.NavigateBack -> {
						onNavigateBack()
					}
				}
			}
		}
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	override val model: StateFlow<InfoStore.State>
		get() = store.stateFlow

	override fun navigateBack() {
		store.accept(InfoStore.Intent.NavigateBack)
	}

	override fun reloadOnError() {
		store.accept(InfoStore.Intent.ReloadOnError)
	}
}