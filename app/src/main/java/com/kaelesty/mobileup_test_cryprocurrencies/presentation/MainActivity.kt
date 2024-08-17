package com.kaelesty.mobileup_test_cryprocurrencies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.kaelesty.mobileup_test_cryprocurrencies.di.ApplicationComponent
import com.kaelesty.mobileup_test_cryprocurrencies.di.DaggerApplicationComponent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.RootComponent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.RootContent
import javax.inject.Inject

class MainActivity : ComponentActivity() {

	private val daggerApplicationComponent by lazy {
		DaggerApplicationComponent
			.factory()
			.create(
				componentContext = defaultComponentContext()
			)
	}
	
	@Inject lateinit var rootComponent: RootComponent


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		daggerApplicationComponent.inject(this@MainActivity)

		//enableEdgeToEdge()
		setContent {
			RootContent(component = rootComponent)
		}
	}
}
