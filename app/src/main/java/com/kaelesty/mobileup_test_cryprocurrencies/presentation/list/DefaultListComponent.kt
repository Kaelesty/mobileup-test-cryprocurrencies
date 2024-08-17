package com.kaelesty.mobileup_test_cryprocurrencies.presentation.list

import android.content.Context
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.kaelesty.mobileup_test_cryprocurrencies.R
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Named

class DefaultListComponent @AssistedInject constructor(
	@Assisted componentContext: ComponentContext,
	@Assisted private val onNavigateToInfoScreen: (Currency.Meta) -> Unit,
	@Named("onToast") private val onToast: (String) -> Unit,
	private val context: Context,
	private val storeFactory: ListStoreFactory,
): ListComponent, ComponentContext by componentContext {

	private val scope = CoroutineScope(Dispatchers.Main)

	private val store = instanceKeeper.getStore {
		storeFactory.create()
	}.apply {

		scope.launch {
			labels.collect { label ->
				when (label) {
					is ListStore.Label.NavigateToInfoScreen -> {
						onNavigateToInfoScreen(label.currencyMeta)
					}
					is ListStore.Label.RefreshingError -> {
						onToast(context.getString(R.string.error_on_refresh))
					}
				}
			}
		}

		doOnDestroy { scope.cancel() }
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	override val model: StateFlow<ListStore.State>
		get() = store.stateFlow

	override fun onPullToRefresh() {
		store.accept(
			ListStore.Intent.Refresh
		)
	}

	override fun onSwitchPriceType(priceType: PriceType) {
		store.accept(
			ListStore.Intent.SwitchPriceType(priceType)
		)
	}

	override fun onReload() {
		store.accept(
			ListStore.Intent.Reload
		)
	}

	override fun onCurrencyClick(currency: Currency) {
		store.accept(
			ListStore.Intent.SelectCurrency(currency.meta)
		)
	}

	@AssistedFactory
	interface Factory {

		fun create(
			@Assisted componentContext: ComponentContext,
			@Assisted onNavigateToInfoScreen: (Currency.Meta) -> Unit,
		): DefaultListComponent
	}
}