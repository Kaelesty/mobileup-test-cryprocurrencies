package com.kaelesty.mobileup_test_cryprocurrencies.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaelesty.mobileup_test_cryprocurrencies.R
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.ErrorScreen
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListContent(
	component: ListComponent
) {

	val state by component.model.collectAsState()

	Scaffold(
		topBar = {
			TopAppBar(title = {
				Column(
					modifier = Modifier
						.padding(4.dp)
				) {
					Text(
						text = stringResource(R.string.list_of_cryptocurrencies),
						fontWeight = FontWeight.Bold,
						fontSize = 24.sp
					)
					Spacer(modifier = Modifier.height(8.dp))
					PriceTypesList(state, component)
				}
			})
		}
	) {
		Box(modifier = Modifier.padding(it)) {
			when (val currentState = state) {
				is ListStore.State.Default -> {
					ListScreenDefault(currentState, component)
				}
				is ListStore.State.Error -> {
					ErrorScreen {
						component.onReload()
					}
				}
				is ListStore.State.Loading -> {
					LoadingScreen()
				}
			}
		}
	}
}

@Composable
private fun ListScreenError(component: ListComponent) {
	Column(
		modifier = Modifier
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Spacer(modifier = Modifier.height(200.dp))
		// TODO insert Bitcoin image
		Text(
			text = stringResource(R.string.something_went_wrong),
			fontSize = 16.sp
		)
		Text(
			text = stringResource(R.string.retry),
			fontSize = 16.sp
		)
		Spacer(modifier = Modifier.height(8.dp))
		with(ButtonDefaults.buttonColors()) {
			Button(
				onClick = { component.onReload() },
				colors = ButtonColors(
					containerColor = Color.Yellow, // TODO change to orange
					contentColor, disabledContainerColor, disabledContentColor
				)
			) {
				Text(text = stringResource(R.string.RETRY))
			}
		}

	}
}

@Composable
private fun ListScreenDefault(
	currentState: ListStore.State.Default,
	component: ListComponent
) {
	LazyColumn {
		items(currentState.currencies, key = { it.meta.apiId }) {
			CurrencyBlock(component, it)
		}
	}
}

@Composable
private fun CurrencyBlock(
	component: ListComponent,
	it: Currency
) {
	Row(
		modifier = Modifier.clickable {
			component.onCurrencyClick(it)
		}
	) {
		GlideImage(
			model = it.meta.imageUrl,
			contentDescription = stringResource(R.string.cryptocurrency_logo),
			Modifier.size(60.dp),
		)
		Column(
			Modifier.weight(1f)
		) {
			Text(
				text = it.meta.name,
				fontSize = 18.sp,
				fontWeight = FontWeight.Bold
			)
			Text(
				text = it.meta.shortName,
				fontSize = 18.sp,
				color = Color.Gray
			)
		}
		Column {
			Text(
				text = "${it.pricing.priceType.symbol} ${"%.2f".format(it.pricing.price)}",
				fontSize = 18.sp,
				fontWeight = FontWeight.ExtraBold
			)
			it.pricing.changePercentage.let {
				Text(
					text = "${if (it >= 0) "+" else "-"} $it",
					fontSize = 18.sp,
					color = if (it >= 0) Color.Green else Color.Red
				)
			}
		}
	}
}

@Composable
private fun PriceTypesList(
	state: ListStore.State,
	component: ListComponent
) {
	Row {
		PriceType.entries.forEach {
			ElevatedFilterChip(
				selected = it == state.priceType,
				onClick = {
					component.onSwitchPriceType(it)
				},
				label = {
					Text(text = it.title)
				}
			)
		}
	}
}