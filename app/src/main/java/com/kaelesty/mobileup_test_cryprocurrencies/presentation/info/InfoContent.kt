package com.kaelesty.mobileup_test_cryprocurrencies.presentation.info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kaelesty.mobileup_test_cryprocurrencies.R
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.ErrorScreen
import com.kaelesty.mobileup_test_cryprocurrencies.presentation.root.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun InfoContent(
	component: InfoComponent
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
						text = state.meta.name,
						fontWeight = FontWeight.Bold,
						fontSize = 24.sp
					)
					Spacer(modifier = Modifier.height(8.dp))
				}
			})
		}
	) {
		Box(modifier = Modifier.padding(it)) {
			when (val currentState = state) {
				is InfoStore.State.Default -> {

					val scrollState = rememberScrollState()

					Column(
						modifier = Modifier
							.padding(16.dp)
							.verticalScroll(scrollState)
					) {
						GlideImage(
							model = state.meta.imageUrl,
							contentDescription = stringResource(R.string.cryptocurrency_logo),
							Modifier.size(80.dp),
						)
						Text(
							text = stringResource(R.string.description),
							fontSize = 24.sp,
							fontWeight = FontWeight.Bold
						)
						Text(
							text = currentState.info.description,
							fontSize = 20.sp
						)
						Spacer(modifier = Modifier.height(8.dp))
						Text(
							text = "Categories: ${currentState.info.categories.joinToString(",")}",
							fontSize = 20.sp
						)
					}
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