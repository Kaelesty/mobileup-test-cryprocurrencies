package com.kaelesty.mobileup_test_cryprocurrencies.di

import com.kaelesty.mobileup_test_cryprocurrencies.data.apiservice.CurrenciesApiService
import com.kaelesty.mobileup_test_cryprocurrencies.data.apiservice.CurrenciesApiServiceImpl
import com.kaelesty.mobileup_test_cryprocurrencies.data.repositories.CurrenciesRepositoryImpl
import com.kaelesty.mobileup_test_cryprocurrencies.domain.repositories.CurrenciesRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoriesModule {

	@Binds
	fun bindCurrentiesRepository(impl: CurrenciesRepositoryImpl): CurrenciesRepository

}