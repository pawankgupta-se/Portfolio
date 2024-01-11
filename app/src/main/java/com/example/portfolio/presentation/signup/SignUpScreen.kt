package com.example.portfolio.presentation.signup

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.portfolio.BuildConfig
import com.example.portfolio.R
import com.example.portfolio.presentation.common.Avatar
import com.example.portfolio.presentation.common.CustomButton
import com.example.portfolio.presentation.common.CustomTextField
import com.example.portfolio.presentation.common.TopBarWithSubtitle
import com.example.portfolio.ui.theme.Dimens
import com.example.portfolio.util.createImageFile
import java.util.Objects


@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel,
    onSignUpSuccess: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.uiState

    val uri = uriBuilder(context)

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            result->
            if(result) {
                viewModel.onEvent(SignUpFormEvent.AvatarChanged(uri.toString()))
            }
        }

    val permissionLauncher = rememberPermissionLauncher(uri, cameraLauncher)

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    onSignUpSuccess()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBarWithSubtitle(
                title = stringResource(R.string.sign_up_screen_title),
                subtitle = stringResource(R.string.sign_up_page_help)
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
                Avatar(
                    uri = state.avatar,
                    onClick = {
                        launchCamera(context, cameraLauncher, uri, permissionLauncher)
                    }
                )

                FormSection(state, viewModel)

                Spacer(modifier = Modifier.weight(1f))

                CustomButton(label = stringResource(R.string.submit_button_text)) {
                    viewModel.onEvent(SignUpFormEvent.Submit)
                }
            }
        },
    )
}

@Composable
private fun FormSection(
    state: SignUpScreenState,
    viewModel: SignUpViewModel
) {
    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.firstName,
        hint = stringResource(R.string.first_name),
        onTextChange = { viewModel.onEvent(SignUpFormEvent.FirstNameChanged(it)) })
    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        hint = stringResource(R.string.email_address),
        value = state.email,
        error = state.emailError ?: "",
        isEmail = true,
        onTextChange = { viewModel.onEvent(SignUpFormEvent.EmailChanged(it)) })
    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.password,
        error = state.passwordError ?: "",
        hint = stringResource(R.string.password),
        isPassword = true,
        onTextChange = { viewModel.onEvent(SignUpFormEvent.PasswordChanged(it)) })
    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        hint = stringResource(R.string.website),
        value = state.website,
        onTextChange = { viewModel.onEvent(SignUpFormEvent.WebsiteChanged(it)) })
}

private fun launchCamera(
    context: Context,
    cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
    uri: Uri,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>
) {
    val permissionCheckResult =
        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        cameraLauncher.launch(uri)
    } else {
        // Request a permission
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }
}


private fun uriBuilder(context: Context): Uri {
    val file = context.createImageFile()
    return FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )
}
@Composable
private fun rememberPermissionLauncher(
    uri: Uri,
    cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
): ManagedActivityResultLauncher<String, Boolean> {

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    return permissionLauncher
}


