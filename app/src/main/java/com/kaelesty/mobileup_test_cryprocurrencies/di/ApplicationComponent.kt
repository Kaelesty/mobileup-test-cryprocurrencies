package com.kaelesty.mobileup_test_cryprocurrencies.di

import com.arkivanov.decompose.ComponentContext
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(
	modules = [
		ApiModule::class,
		RepositoriesModule::class
	]
)
interface ApplicationComponent {

	fun inject(activity: MainActivity)

	@Component.Factory
	interface ApplicationComponentFactory {

		fun create(
			@BindsInstance componentContext: ComponentContext
		): ApplicationComponent
	}
}