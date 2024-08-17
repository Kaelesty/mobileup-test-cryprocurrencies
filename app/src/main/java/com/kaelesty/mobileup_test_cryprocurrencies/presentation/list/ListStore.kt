package com.kaelesty.mobileup_test_cryprocurrencies.presentation.list

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import com.kaelesty.mobileup_test_cryprocurrencies.domain.usecases.GetCurrenciesListUseCase
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.list.ListStore.Intent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.list.ListStore.Label
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.list.ListStore.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ListStore : Store<Intent, State, Label> {

    sealed interface Intent {

		class SwitchPriceType(val priceType: PriceType): Intent

		data object Reload: Intent

		class SelectCurrency(val currencyMeta: Currency.Meta): Intent

		data object Refresh: Intent
    }

    sealed class State(
		val priceType: PriceType
	) {

		class Loading(priceType: PriceType): State(priceType)

		class Error(priceType: PriceType): State(priceType)

		class Default(
			priceType: PriceType,
			val currencies: List<Currency>,
			val isRefreshing: Boolean = false
		): State(priceType)
	}

    sealed interface Label {

		class NavigateToInfoScreen(val currencyMeta: Currency.Meta): Label

		data object RefreshingError: Label
    }
}

class ListStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
	private val getCurrenciesListUseCase: GetCurrenciesListUseCase,
) {

	private val scope = CoroutineScope(
		Dispatchers.IO +
				SupervisorJob()
	)

    fun create(): ListStore =
        object : ListStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ListStore",
            initialState = State.Loading(
				priceType = PriceType.USD
			),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    sealed interface Action {

		data object LoadList: Action
    }

    private sealed interface Msg {

		class SetNewCurrenciesList(val list: List<Currency>): Msg

		data object SetError: Msg

		data object SetLoading: Msg

		class SetRefreshing(val value: Boolean): Msg

		class SetPriceType(val priceType: PriceType): Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
			dispatch(Action.LoadList)
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
			when (intent) {
				is Intent.Refresh -> {
					dispatch(Msg.SetRefreshing(true))
					reloadCurrenciesList(
						priceType = getState().priceType,
						onException = {
							dispatch(Msg.SetRefreshing(false))
							publish(Label.RefreshingError)
						},
						onFinish = {
							dispatch(Msg.SetRefreshing(false))
						},
						setLoading = false
					)
				}

				is Intent.Reload -> {
					reloadCurrenciesList(getState().priceType)
				}
				is Intent.SelectCurrency -> {
					publish(Label.NavigateToInfoScreen(intent.currencyMeta))
				}
				is Intent.SwitchPriceType -> {
					dispatch(Msg.SetPriceType(intent.priceType))
					reloadCurrenciesList(intent.priceType)
				}
			}
        }

        override fun executeAction(action: Action, getState: () -> State) {
			when (action) {
				Action.LoadList -> reloadCurrenciesList(getState().priceType)
			}
        }

		private fun reloadCurrenciesList(
			priceType: PriceType,
			onException: () -> Unit = { dispatch(Msg.SetError) },
			onFinish: (() -> Unit)? = null,
			setLoading: Boolean = true
		) {
			if (setLoading) dispatch(Msg.SetLoading)
			scope.launch {
				try {
					val newCurrenciesList = getCurrenciesListUseCase(priceType)
					dispatch(Msg.SetNewCurrenciesList(newCurrenciesList))
					onFinish?.let {
						it()
					}
				}
				catch (e: Exception) {
					onException()
				}
			}
		}
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
				is Msg.SetError -> State.Error(priceType)
				is Msg.SetNewCurrenciesList -> State.Default(priceType, message.list)
				is Msg.SetPriceType -> State.Loading(message.priceType)
				is Msg.SetLoading -> State.Loading(priceType)
				is Msg.SetRefreshing -> {
					if (this is State.Default) {
						State.Default(
							this.priceType,
							this.currencies,
							message.value
						)
					}
					else this
				}
			}
    }
}
