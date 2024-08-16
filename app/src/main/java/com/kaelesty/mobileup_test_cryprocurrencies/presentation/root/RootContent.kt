package com.kaelesty.mobileup_test_cryprocurrencies.presentation.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.info.InfoContent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.list.ListContent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.theme.AppTheme

@Composable
fun RootContent(
	component: RootComponent
) {

	AppTheme {
		Children(
			stack = component.stack,
			modifier = Modifier.fillMaxSize()
		) {
			when (val instance = it.instance) {
				is RootComponent.Child.Info -> InfoContent(component = instance.component)
				is RootComponent.Child.List -> ListContent(component = instance.component)
			}
		}
	}
}