package com.kaelesty.mobileup_test_cryprocurrencies.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.info.InfoComponent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.list.ListComponent

interface RootComponent {

	val stack: Value<ChildStack<*, Child>>

	sealed interface Child {

		class List(val component: ListComponent): Child

		class Info(val component: InfoComponent): Child
	}
}