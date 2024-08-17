package com.kaelesty.mobileup_test_cryprocurrencies.presentation

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.kaelesty.mobileup_test_cryprocurrencies.di.DaggerApplicationComponent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.RootComponent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.RootContent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.theme.RedError
import io.github.muddz.styleabletoast.StyleableToast
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import javax.inject.Inject

class MainActivity : ComponentActivity() {

	private val httpClient: HttpClient by lazy {
		HttpClient(Android) {
			expectSuccess = true
		}
	}

	private val daggerApplicationComponent by lazy {
		DaggerApplicationComponent
			.factory()
			.create(
				componentContext = defaultComponentContext(),
				activityContext = this,
				onToast = ::showErrorToast,
				onOpenUrl = ::openURL,
				httpClient = httpClient
			)
	}

	private fun openURL(url: String) {
		startActivity(
			Intent(Intent.ACTION_VIEW, Uri.parse(url))
		)
	}

	private fun showErrorToast(it: String) {
		StyleableToast.Builder(this)
			.text(it)
			.textColor(Color.WHITE)
			.backgroundColor(
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
					Color.rgb(
						RedError.red, RedError.green, RedError.blue
					)
				} else {
					Color.RED
				}
			)
			.cornerRadius(4)
			.show()
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

	override fun onDestroy() {
		super.onDestroy()
		httpClient.close()
	}
}
