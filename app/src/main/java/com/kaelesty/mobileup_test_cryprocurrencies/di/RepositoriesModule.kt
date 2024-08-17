package com.kaelesty.mobileup_test_cryprocurrencies.di

import com.kaelesty.mobileup_test_cryprocurrencies.data.repositories.CurrenciesRepositoryImpl
import com.kaelesty.mobileup_test_cryprocurrencies.domain.repositories.CurrenciesRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoriesModule {

	@Binds
	fun bindCurrensiesRepository(impl: CurrenciesRepositoryImpl): CurrenciesRepository

}