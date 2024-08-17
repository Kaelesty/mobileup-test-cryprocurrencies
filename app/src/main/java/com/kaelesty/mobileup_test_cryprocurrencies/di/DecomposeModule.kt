package com.kaelesty.mobileup_test_cryprocurrencies.di

import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.DefaultRootComponent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.RootComponent
import dagger.Binds
import dagger.Module

@Module
interface DecomposeModule {

	@Binds
	fun bindRootComponent(impl: DefaultRootComponent): RootComponent
}