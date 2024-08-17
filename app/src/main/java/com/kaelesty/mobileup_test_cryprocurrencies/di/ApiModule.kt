package com.kaelesty.mobileup_test_cryprocurrencies.di

import com.kaelesty.mobileup_test_cryprocurrencies.data.apiservice.CurrenciesApiService
import com.kaelesty.mobileup_test_cryprocurrencies.data.apiservice.CurrenciesApiServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import kotlinx.serialization.json.Json

@Module
interface ApiModule {

	@Binds
	fun bindCurrentiesApiService(impl: CurrenciesApiServiceImpl): CurrenciesApiService

	companion object {

		@Provides
		fun provideJson(): Json = Json { ignoreUnknownKeys = true }

		@Provides
		fun provideHttpClient(): HttpClient = HttpClient(Android) {
			expectSuccess = true
		}
	}

}