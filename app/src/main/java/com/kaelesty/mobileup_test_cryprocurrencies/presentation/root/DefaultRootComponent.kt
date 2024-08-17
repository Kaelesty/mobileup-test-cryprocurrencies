package com.kaelesty.mobileup_test_cryprocurrencies.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.info.DefaultInfoComponent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.list.DefaultListComponent
import kotlinx.serialization.Serializable
import javax.inject.Inject

class DefaultRootComponent @Inject constructor(
	componentContext: ComponentContext,
	private val infoComponentFactory: DefaultInfoComponent.Factory,
	private val listComponentFactory: DefaultListComponent.Factory,
): RootComponent, ComponentContext by componentContext {

	private val navigation = StackNavigation<Config>()

	override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
		source = navigation,
		initialConfiguration = Config.List,
		handleBackButton = true,
		serializer = Config.serializer(),
		childFactory = ::child
	)

	private fun child(
		config: Config,
		componentContext: ComponentContext,
	): RootComponent.Child = when (config) {
		is Config.Info -> {
			RootComponent.Child.Info(
				component = infoComponentFactory.create(
					componentContext = componentContext,
					currencyMeta = config.currencyMeta,
					onNavigateBack = {
						navigation.pop()
					}
				)
			)
		}
		is Config.List -> {
			RootComponent.Child.List(
				component = listComponentFactory.create(
					componentContext = componentContext,
					onNavigateToInfoScreen = {
						navigation.push(Config.Info(it))
					}
				)
			)
		}
	}

	@Serializable
	private sealed interface Config {

		@Serializable
		data object List: Config

		@Serializable
		class Info(val currencyMeta: Currency.Meta): Config
	}
}