package com.nickcook.zeldex.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nickcook.zeldex.R

@Preview(showBackground = true)
@Composable
fun ErrorState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(id = R.string.error_message),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(16.dp)
        )
    }
}
