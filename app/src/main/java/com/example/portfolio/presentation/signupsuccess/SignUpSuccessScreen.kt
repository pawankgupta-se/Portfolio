package com.example.portfolio.presentation.signupsuccess

import android.content.ActivityNotFoundException
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.example.portfolio.R
import com.example.portfolio.presentation.common.Avatar
import com.example.portfolio.presentation.common.CustomButton
import com.example.portfolio.presentation.common.TopBarWithSubtitle
import com.example.portfolio.ui.theme.Dimens
import com.example.portfolio.ui.theme.PortfolioTheme


@Composable
fun SignUpSuccessScreen(modifier: Modifier = Modifier, state: SignUpSuccessState) {
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopBarWithSubtitle(
                title = stringResource(R.string.sign_up_success_screen_title, state.firstName),
                subtitle = stringResource(R.string.sign_up_success_screen_subtitle)
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(horizontal = Dimens.spacingNormal)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
            ) {
                if (state.avatar.isNotBlank()) {
                    Avatar(
                        uri = state.avatar,
                    )
                } else {
                    Avatar(painter = painterResource(id = R.drawable.profile_placeholder))
                }

                ClickableText(
                    text = AnnotatedString(state.website),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    ),
                ) {
                    try {
                        uriHandler.openUri(state.website)
                    } catch (e: ActivityNotFoundException) {
                        Log.d("", "Unable to open the link")
                    }
                }

                Text(text = state.firstName, style = MaterialTheme.typography.bodyMedium)
                Text(text = state.email, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.weight(1f))
                CustomButton(label = stringResource(R.string.sign_in_button_text))
            }
        },
    )
}

@Preview
@Composable
fun SignUpSuccessScreenPreview() {
    PortfolioTheme {
        SignUpSuccessScreen(state = SignUpSuccessState())
    }
}