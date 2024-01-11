package com.example.portfolio.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.portfolio.ui.theme.Dimens
import com.example.portfolio.ui.theme.PortfolioTheme

@Composable
fun CustomButton(modifier: Modifier = Modifier, label: String, onClick: () -> Unit = {}) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(Dimens.spacingMedium),
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.buttonHeight)
            .padding(vertical = Dimens.spacingNormal),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xfff44336))
    ) {
        Text(text = label)
    }
}

@Preview
@Composable
fun CustomButtonPrev() {
    PortfolioTheme {
        CustomButton(label = "Submit")
    }
}