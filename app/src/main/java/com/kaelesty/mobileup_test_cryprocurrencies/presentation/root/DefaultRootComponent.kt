package com.kaelesty.mobileup_test_cryprocurrencies.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.list.DefaultListComponent
import javax.inject.Inject

class DefaultRootComponent @Inject constructor(
	componentContext: ComponentContext,

): RootComponent, ComponentContext by componentContext {

	override val stack: Value<ChildStack<*, RootComponent.Child>>
		get() = TODO("Not yet implemented")
}