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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
  modifier: Modifier = Modifier,
  onBack: () -> Unit,
  onSignIn: () -> Unit,
  onNavigateToSignUp: () -> Unit,
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
        onClick = onSignIn,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(text = "Sign In")
      }
      Spacer(modifier = Modifier.height(16.dp))
      NavigateToSignInText(onNavigateToSignUp)
    }
  }
}

@Composable
private fun NavigateToSignInText(
  onNavigateToSignUp: () -> Unit,
) {
  val annotatedString = buildAnnotatedString {
    withStyle(
      style = MaterialTheme.typography
        .bodySmall
        .copy(MaterialTheme.colorScheme.onSurfaceVariant)
        .toSpanStyle()
    ) {
      append("Don't have an account? ")
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
      append("Sign Up")
      addStringAnnotation(
        tag = "SignUp",
        start = 0,
        end = 7,
        annotation = "SignUp"
      )
    }
  }

  @Suppress("DEPRECATION")
  ClickableText(
    annotatedString,
    style = MaterialTheme.typography.bodySmall,
    onClick = {
      annotatedString.getStringAnnotations("SignUp", 0, 7)
        .firstOrNull()
        ?.let { onNavigateToSignUp() }
    }
  )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignInScreenPreview() {
  DicodingStoryTheme {
    SignInScreen(
      onBack = {},
      onSignIn = {},
      onNavigateToSignUp = {}
    )
  }
}