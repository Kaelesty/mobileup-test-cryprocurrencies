package com.kaelesty.mobileup_test_cryprocurrencies.presentation.info

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.CurrencyInfo
import com.kaelesty.mobileup_test_cryprocurrencies.domain.usecases.GetCurrencyInfoUseCase
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.info.InfoStore.Intent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.info.InfoStore.Label
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.info.InfoStore.State
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.list.ListStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

interface InfoStore : Store<Intent, State, Label> {

    sealed interface Intent {

        object ReloadOnError: Intent

        object NavigateBack: Intent
    }

    sealed class State(
        val meta: Currency.Meta
    ) {

        class Loading(meta: Currency.Meta): State(meta)

        class Error(meta: Currency.Meta): State(meta)

        class Default(meta: Currency.Meta, val info: CurrencyInfo): State(meta)

    }

    sealed interface Label {

        object NavigateBack: Label
    }
}

class InfoStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getCurrencyInfoUseCase: GetCurrencyInfoUseCase,
) {

    private val scope = CoroutineScope(
        Dispatchers.IO +
                SupervisorJob()
    )

    fun create(
        currencyMeta: Currency.Meta
    ): InfoStore =
        object : InfoStore, Store<Intent, State, Label> by storeFactory.create(
            name = "InfoStore",
            initialState = State.Loading(currencyMeta),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {

        object LoadInfo: Action
    }

    private sealed interface Msg {

        class SetInfo(val currencyInfo: CurrencyInfo): Msg

        object SetError: Msg

        object SetLoading: Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            dispatch(Action.LoadInfo)
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.NavigateBack -> {
                    publish(Label.NavigateBack)
                }
                is Intent.ReloadOnError -> {
                    reloadInfo(getState().meta.apiId)
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                Action.LoadInfo -> {
                    reloadInfo(getState().meta.apiId)
                }
            }
        }

        private fun reloadInfo(currencyApiId: String) {
            dispatch(Msg.SetLoading)
            try {
                scope.launch {
                    val currencyInfo = getCurrencyInfoUseCase(currencyApiId)
                    dispatch(Msg.SetInfo(currencyInfo))
                }
            }
            catch (e: Exception) {
                dispatch(Msg.SetError)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.SetError -> {
                    State.Error(meta)
                }
                is Msg.SetInfo -> {
                    State.Default(meta, message.currencyInfo)
                }
                is Msg.SetLoading -> {
                    State.Loading(meta)
                }
            }
    }
}
