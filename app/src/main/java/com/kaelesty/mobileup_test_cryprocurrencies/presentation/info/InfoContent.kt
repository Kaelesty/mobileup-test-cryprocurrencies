package com.kaelesty.mobileup_test_cryprocurrencies.presentation.info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kaelesty.mobileup_test_cryprocurrencies.R
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.removeHyperlinks
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.ErrorScreen
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.LoadingScreen
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.TopBar

@Composable
fun InfoContent(
	component: InfoComponent
) {
	val state by component.model.collectAsState()

	Scaffold(
		topBar = {
			TopBar {
				Row(
					verticalAlignment = Alignment.CenterVertically
				) {
					IconButton(onClick = { component.navigateBack() }) {
						Icon(
							Icons.AutoMirrored.Filled.ArrowBack,
							contentDescription = stringResource(R.string.back_button)
						)
					}
					Text(
						text = state.meta.name,
						fontWeight = FontWeight.SemiBold,
						fontSize = 24.sp
					)	
				}
				Spacer(modifier = Modifier.height(8.dp))
			}
		}
	) {
		Box(modifier = Modifier.padding(it)) {
			when (val currentState = state) {
				is InfoStore.State.Default -> {

					InfoScreenDefault(state, currentState)
				}
				is InfoStore.State.Error -> {
					ErrorScreen {
						component.reloadOnError()
					}
				}
				is InfoStore.State.Loading -> {
					LoadingScreen()
				}
			}
		}
	}
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun InfoScreenDefault(
	state: InfoStore.State,
	currentState: InfoStore.State.Default
) {
	val scrollState = rememberScrollState()

	Column(
		modifier = Modifier
			.padding(16.dp)
			.verticalScroll(scrollState),
	) {
		Spacer(modifier = Modifier.height(8.dp))
		GlideImage(
			model = state.meta.imageUrl,
			contentDescription = stringResource(R.string.cryptocurrency_logo),
			Modifier
				.fillMaxWidth()
				.size(120.dp),
		)
		Text(
			text = stringResource(R.string.description),
			fontSize = 24.sp,
			fontWeight = FontWeight.SemiBold
		)
		Spacer(modifier = Modifier.height(8.dp))
		Text(
			text = currentState.info.description.removeHyperlinks(),
			fontSize = 20.sp
		)
		Spacer(modifier = Modifier.height(8.dp))
		Text(
			text = stringResource(R.string.categories),
			fontSize = 24.sp,
			fontWeight = FontWeight.SemiBold
		)
		Spacer(modifier = Modifier.height(8.dp))
		Text(
			text = currentState.info.categories.joinToString(", "),
			fontSize = 20.sp
		)
	}
}