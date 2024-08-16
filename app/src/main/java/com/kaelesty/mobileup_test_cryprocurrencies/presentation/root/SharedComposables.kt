package com.kaelesty.mobileup_test_cryprocurrencies.presentation.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaelesty.mobileup_test_cryprocurrencies.R
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.list.ListComponent

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
				onClick = onRetry,
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
fun LoadingScreen() {
	Box(
		modifier = Modifier
			.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		CircularProgressIndicator(
			color = Color.Yellow, // TODO change to orange
			modifier = Modifier.size(60.dp)
		)
	}
}