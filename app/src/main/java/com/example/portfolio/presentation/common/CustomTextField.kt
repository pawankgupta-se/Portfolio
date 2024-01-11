package com.example.portfolio.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.portfolio.ui.theme.Dimens
import com.example.portfolio.ui.theme.PortfolioTheme

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    value: String,
    hint: String = "",
    error: String = "",
    isPassword: Boolean = false,
    isEmail: Boolean = false
) {

    Column {

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = Dimens.spacingSmall)
            )
        }

        OutlinedTextField(
            shape = RoundedCornerShape(Dimens.spacingMedium),
            modifier = modifier,
            value = value,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(text = hint)
            },
            isError = error.isNotBlank(),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = if (isEmail) KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ) else KeyboardOptions(),
        )

    }

}


@Preview
@Composable
fun CustomFieldTextPrev() {
    PortfolioTheme {
        CustomTextField(value = "", onTextChange = {}, hint = "Email")
    }

}