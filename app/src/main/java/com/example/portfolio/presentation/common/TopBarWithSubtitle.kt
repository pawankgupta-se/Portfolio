package com.example.portfolio.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.portfolio.ui.theme.Dimens
import com.example.portfolio.ui.theme.PortfolioTheme

@Composable
fun TopBarWithSubtitle(modifier: Modifier = Modifier, title: String, subtitle: String = "") {
    Column(modifier = modifier.fillMaxWidth().padding(Dimens.spacingNormal)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun TopBarPreview() {
    PortfolioTheme {
        TopBarWithSubtitle(title = "Title", subtitle = "Subtitle")
    }
}