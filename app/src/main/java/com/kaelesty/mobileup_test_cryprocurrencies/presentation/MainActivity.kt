package com.kaelesty.mobileup_test_cryprocurrencies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.kaelesty.mobileup_test_cryprocurrencies.di.DaggerApplicationComponent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.RootComponent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.RootContent
import javax.inject.Inject

class MainActivity : ComponentActivity() {

	private val component by lazy {
		DaggerApplicationComponent
			.factory()
			.create(
				componentContext = defaultComponentContext()
			)
	}
	
	@Inject lateinit var rootComponent: RootComponent


	override fun onCreate(savedInstanceState: Bundle?) {

		component.inject(this@MainActivity)

		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			RootContent(component = rootComponent)
		}
	}
}
