package com.kaelesty.mobileup_test_cryprocurrencies.presentation.list

import android.util.Log
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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ListStore : Store<Intent, State, Label> {

    sealed interface Intent {

		class SwitchPriceType(val priceType: PriceType): Intent

		object Reload: Intent

		class SelectCurrency(val currencyMeta: Currency.Meta): Intent
    }

    sealed class State(
		val priceType: PriceType
	) {

		class Loading(priceType: PriceType): State(priceType)

		class Error(priceType: PriceType): State(priceType)

		class Default(
			priceType: PriceType,
			val currencies: List<Currency>
		): State(priceType)
	}

    sealed interface Label {

		class NavigateToInfoScreen(val currencyMeta: Currency.Meta): Label
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

		object LoadList: Action
    }

    private sealed interface Msg {

		class SetNewCurrenciesList(val list: List<Currency>): Msg

		object SetError: Msg

		object SetLoading: Msg

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

		private fun reloadCurrenciesList(priceType: PriceType) {
			dispatch(Msg.SetLoading)
			scope.launch {
				try {
					val newCurrenciesList = getCurrenciesListUseCase(priceType)
					dispatch(Msg.SetNewCurrenciesList(newCurrenciesList))
				}
				catch (e: Exception) {
					dispatch(Msg.SetError)
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
			}
    }
}
