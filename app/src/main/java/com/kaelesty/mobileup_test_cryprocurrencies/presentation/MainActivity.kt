package com.kaelesty.mobileup_test_cryprocurrencies.presentation

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.kaelesty.mobileup_test_cryprocurrencies.di.ApplicationComponent
import com.kaelesty.mobileup_test_cryprocurrencies.di.DaggerApplicationComponent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.RootComponent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.RootContent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.theme.RedError
import io.github.muddz.styleabletoast.StyleableToast
import javax.inject.Inject

class MainActivity : ComponentActivity() {

	private val daggerApplicationComponent by lazy {
		DaggerApplicationComponent
			.factory()
			.create(
				componentContext = defaultComponentContext(),
				activityContext = this,
				onToast = {
					StyleableToast.Builder(this)
						.text(it)
						.textColor(Color.WHITE)
						.backgroundColor(
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
								Color.rgb(
									RedError.red, RedError.green, RedError.blue
								)
							}
							else {
								Color.RED
							}
						)
						.cornerRadius(4)
						.show()
				}
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
