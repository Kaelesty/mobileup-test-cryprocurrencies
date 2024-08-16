package com.kaelesty.mobileup_test_cryprocurrencies.di

import com.kaelesty.mobileup_test_cryprocurrencies.data.apiservice.CurrenciesApiService
import com.kaelesty.mobileup_test_cryprocurrencies.data.apiservice.CurrenciesApiServiceImpl
import dagger.Binds
import dagger.Module

@Module
interface ApiModule {

	@Binds
	fun bindCurrentiesApiService(impl: CurrenciesApiServiceImpl): CurrenciesApiService

}