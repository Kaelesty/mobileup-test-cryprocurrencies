package com.kaelesty.mobileup_test_cryprocurrencies.presentation.root

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaelesty.mobileup_test_cryprocurrencies.R
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.list.ListComponent
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.list.ListStore
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.theme.OrangePrimary

@Composable
fun TopBar(
	content: @Composable () -> Unit,
) {
	Surface(
		shadowElevation = 4.dp,
		modifier = Modifier
			.fillMaxWidth()
	) {
		Column(
			modifier = Modifier
				.padding(horizontal = 12.dp)
		) {
			content()
		}
	}
}

@Composable
fun ErrorScreen(
	onRetry: () -> Unit
) {
	Column(
		modifier = Modifier
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Spacer(modifier = Modifier.height(200.dp))
		Image(
			painter = painterResource(id = R.drawable.bitcoin_logo),
			contentDescription = stringResource(R.string.btc_logo),
			modifier = Modifier.size(120.dp)
		)
		Spacer(modifier = Modifier.height(16.dp))
		Text(
			text = stringResource(R.string.something_went_wrong),
			fontSize = 16.sp
		)
		Text(
			text = stringResource(R.string.retry),
			fontSize = 16.sp
		)
		Spacer(modifier = Modifier.height(16.dp))
		with(ButtonDefaults.buttonColors()) {
			Button(
				onClick = onRetry,
				colors = ButtonColors(
					containerColor = OrangePrimary,
					contentColor, disabledContainerColor, disabledContentColor
				),
			) {
				Text(text = stringResource(R.string.RETRY))
			}
		}

	}
}

@Composable
fun LoadingScreen() {
	Box(
		modifier = Modifier
			.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		CircularProgressIndicator(
			color = OrangePrimary,
			modifier = Modifier.size(44.dp)
		)
	}
}