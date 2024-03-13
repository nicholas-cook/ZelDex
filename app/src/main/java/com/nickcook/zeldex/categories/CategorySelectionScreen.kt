package com.nickcook.zeldex.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nickcook.zeldex.R
import com.nickcook.zeldex.core.data.model.CompendiumCategory
import com.nickcook.zeldex.ui.theme.ZelDexTheme

@Composable
fun CategorySelectionRoute(onCategoryClicked: (CompendiumCategory) -> Unit) {
    CategorySelectionScreen(onCategoryClicked = onCategoryClicked)
}

@Composable
fun CategorySelectionScreen(onCategoryClicked: (CompendiumCategory) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = stringResource(id = R.string.title_select_category),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        CompendiumCategory.entries.forEach { category ->
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                onClick = { onCategoryClicked(category) }) {
                Text(text = category.categoryName.uppercase())
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategorySelectionScreenPreview() {
    ZelDexTheme {
        CategorySelectionScreen(onCategoryClicked = {})
    }
}