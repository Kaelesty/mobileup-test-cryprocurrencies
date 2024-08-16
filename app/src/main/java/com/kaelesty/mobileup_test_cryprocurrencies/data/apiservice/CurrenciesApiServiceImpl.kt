package com.kaelesty.mobileup_test_cryprocurrencies.data.apiservice

import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrenciesListResponse
import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrencyInfoResponse
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CurrenciesApiServiceImpl @Inject constructor() : CurrenciesApiService {

	private val BASE_URL = "https://api.coingecko.com/api/v3/"
	private val LIST_URL = "coins/markets/"
	private val INFO_URL = "coins/"

	private val client = HttpClient(Android) {
		expectSuccess = true
	}

	override suspend fun getCurrenciesList(priceType: PriceType) = client
		.get<String>("$BASE_URL$LIST_URL")
		.let {
			Json.decodeFromString<CurrenciesListResponse>(it)
		}

	override suspend fun getCurrencyInfo(id: String) = client
		.get<String>("$BASE_URL$INFO_URL")
		.let {
			Json.decodeFromString<CurrencyInfoResponse>(it)
		}
}