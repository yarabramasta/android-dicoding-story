package dev.ybrmst.dicodingstory.ui.composables.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.ybrmst.dicodingstory.ui.common.keyboardAsState
import dev.ybrmst.dicodingstory.ui.theme.DicodingStoryTheme
import dev.ybrmst.dicodingstory.ui.viewmodel.signup.SignUpState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
  modifier: Modifier = Modifier,
  state: SignUpState,
  onBack: () -> Unit,
  onSignUp: () -> Unit,
  onNavigateToSignIn: () -> Unit,
  onNameInputChanged: (String) -> Unit,
  onEmailInputChanged: (String) -> Unit,
  onPasswordInputChanged: (String) -> Unit,
) {
  val isKeyboardOpen by keyboardAsState()
  BackHandler(enabled = !isKeyboardOpen) {
    onBack()
  }

  Scaffold(
    topBar = {
      TopAppBar(
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(
              Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back"
            )
          }
        },
        title = { },
      )
    }
  ) { innerPadding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Button(
        onClick = onSignUp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(text = "Create an Account")
      }
      Spacer(modifier = Modifier.height(16.dp))
      NavigateToSignInText(onNavigateToSignIn)
    }
  }
}

@Composable
private fun NavigateToSignInText(
  onNavigateToSignIn: () -> Unit,
) {
  val annotatedString = buildAnnotatedString {
    withStyle(
      style = MaterialTheme.typography
        .bodySmall
        .copy(MaterialTheme.colorScheme.onSurfaceVariant)
        .toSpanStyle()
    ) {
      append("Already have an account? ")
    }
    withStyle(
      style = MaterialTheme.typography
        .bodySmall
        .copy(
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.W500,
        )
        .toSpanStyle()
    ) {
      append("Sign In")
      addStringAnnotation(
        tag = "SignIn",
        start = 0,
        end = 7,
        annotation = "SignIn"
      )
    }
  }

  @Suppress("DEPRECATION")
  (ClickableText(
    annotatedString,
    style = MaterialTheme.typography.bodySmall,
    onClick = {
      annotatedString.getStringAnnotations("SignIn", 0, 7)
        .firstOrNull()
        ?.let { onNavigateToSignIn() }
    }
  ))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignUpScreenPreview() {
  DicodingStoryTheme {
    SignUpScreen(
      state = SignUpState.initial(),
      onBack = {},
      onSignUp = {},
      onNavigateToSignIn = {},
      onNameInputChanged = {},
      onEmailInputChanged = {},
      onPasswordInputChanged = {},
    )
  }
}