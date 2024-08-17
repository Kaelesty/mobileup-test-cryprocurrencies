package com.kaelesty.mobileup_test_cryprocurrencies.data.apiservice

import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrenciesListResponse
import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrencyInfoResponse
import com.kaelesty.mobileup_test_cryprocurrencies.data.responses.CurrencyResponse
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CurrenciesApiServiceImpl @Inject constructor(
	private val client: HttpClient,
	private val json: Json
) : CurrenciesApiService {

	private val BASE_URL = "https://api.coingecko.com/api/v3/"

	private val LIST_URL = "coins/markets/"
	private val PRICE_TYPE_PARAM = "vs_currency"
	private val CURRENCIES_COUNT_PARAM = "per_page"

	private val INFO_URL = "coins/"
	private val INFO_DISABLED_PARAMS = listOf(
		"tickers", "market_data", "community_data", "developer_data", "sparkline"
	)

	override suspend fun getCurrenciesList(priceType: PriceType, count: Int) = client
		.get<String>("$BASE_URL$LIST_URL") {
			parameter(PRICE_TYPE_PARAM, priceType.name)
			parameter(CURRENCIES_COUNT_PARAM, count)
		}
		.let {
			json.decodeFromString<List<CurrencyResponse>>(it)
		}

	override suspend fun getCurrencyInfo(id: String) = client
		.get<String>("$BASE_URL$INFO_URL$id") {
			INFO_DISABLED_PARAMS.forEach {
				parameter(it, false)
			}
		}
		.let {
			json.decodeFromString<CurrencyInfoResponse>(it)
		}
}