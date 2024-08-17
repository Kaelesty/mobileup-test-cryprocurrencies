package com.kaelesty.mobileup_test_cryprocurrencies.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Surface
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kaelesty.mobileup_test_cryprocurrencies.R
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.Currency
import com.kaelesty.mobileup_test_cryprocurrencies.domain.entities.PriceType
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.asChangePercentage
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.asPrice
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.ErrorScreen
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.LoadingScreen
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.TopBar
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.theme.OrangeChipBackground
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.theme.OrangeChipText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListContent(
	component: ListComponent
) {

	val state by component.model.collectAsState()

	Scaffold(
		topBar = {
			TopBar {
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					text = stringResource(R.string.list_of_cryptocurrencies),
					fontWeight = FontWeight.SemiBold,
					fontSize = 24.sp
				)
				Spacer(modifier = Modifier.height(8.dp))
				PriceTypesList(state, component)
			}
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
private fun ListScreenDefault(
	currentState: ListStore.State.Default,
	component: ListComponent
) {
	LazyColumn(
		modifier = Modifier
			.padding(top = 4.dp)
			.padding(horizontal = 8.dp)
	) {
		items(currentState.currencies, key = { it.meta.apiId }) {
			CurrencyBlock(component, it)
		}
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CurrencyBlock(
	component: ListComponent,
	it: Currency
) {
	Row(
		modifier = Modifier.clickable {
			component.onCurrencyClick(it)
		},
		verticalAlignment = Alignment.CenterVertically
	) {
		GlideImage(
			model = it.meta.imageUrl,
			contentDescription = stringResource(R.string.cryptocurrency_logo),
			modifier = Modifier
				.padding(4.dp)
				.size(60.dp),
		)
		Column(
			Modifier.weight(1f)
		) {
			Text(
				text = it.meta.name,
				fontSize = 18.sp,
				fontWeight = FontWeight.SemiBold
			)
			Text(
				text = it.meta.shortName.uppercase(),
				fontSize = 18.sp,
				color = Color.Gray
			)
		}
		Column(
			horizontalAlignment = Alignment.End
		) {
			Text(
				text = "${it.pricing.priceType.symbol} ${it.pricing.price.asPrice()}",
				fontSize = 18.sp,
				fontWeight = FontWeight.ExtraBold
			)
			it.pricing.changePercentage.let {
				Text(
					text = "${if (it >= 0) "+" else ""}${it.asChangePercentage()}%",
					fontSize = 18.sp,
					color = if (it >= 0) Color.Green else Color.Red
				)
			}
		}
	}
}

@Composable
fun PriceTypesList(
	state: ListStore.State,
	component: ListComponent
) {
	Row {
		PriceType.entries.forEachIndexed { index, it ->
			if (index != 0) {
				Spacer(modifier = Modifier.width(8.dp))
			}
			ElevatedFilterChip(
				selected = it == state.priceType,
				onClick = {
					component.onSwitchPriceType(it)
				},
				label = {
					Text(text = it.title.uppercase())
				},
				colors = FilterChipDefaults
					.filterChipColors()
					.copy(
						selectedContainerColor = OrangeChipBackground,
						selectedLabelColor = OrangeChipText
					)
			)
		}
	}
}