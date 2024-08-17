package com.kaelesty.mobileup_test_cryprocurrencies.di

import android.content.Context
import com.arkivanov.decompose.ComponentContext
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import io.ktor.client.HttpClient
import javax.inject.Named

@Component(
	modules = [
		ApiModule::class,
		RepositoriesModule::class,
		DecomposeModule::class,
		MviModule::class,
	]
)
interface ApplicationComponent {

	fun inject(activity: MainActivity)

	@Component.Factory
	interface ApplicationComponentFactory {

		fun create(
			@BindsInstance componentContext: ComponentContext,
			@BindsInstance activityContext: Context,
			@BindsInstance @Named("onToast") onToast: (String) -> Unit,
			@BindsInstance @Named("onOpenUrl") onOpenUrl: (String) -> Unit,
			@BindsInstance httpClient: HttpClient
		): ApplicationComponent
	}
}