package com.example.portfolio.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.portfolio.R
import com.example.portfolio.ui.theme.Dimens
import com.example.portfolio.ui.theme.PortfolioTheme

@Composable
fun Avatar(uri: String?, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    val placeholderText = stringResource(R.string.tap_to_add_avatar)
    Box(
        modifier = modifier
            .width(140.dp)
            .height(190.dp)
            .padding(top = Dimens.spacingNormal)
            .clip(RoundedCornerShape(Dimens.spacingMedium))
            .background(
                color = Color.LightGray
            )
            .clickable(onClick = {
                onClick()
            }),
        contentAlignment = Alignment.Center

    ) {

        if (uri.isNullOrEmpty()) {
            Text(
                text = placeholderText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(Dimens.spacingLarge)
            )
        } else {
            AsyncImage(
                model = uri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }

    }
}

@Composable
fun Avatar(painter: Painter, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(140.dp)
            .height(190.dp)
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = Color.LightGray
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}


@Preview
@Composable
fun AvatarPreview() {
    PortfolioTheme {
        Avatar(painter = painterResource(R.drawable.profile_placeholder))
    }

}