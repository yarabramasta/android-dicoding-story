package dev.ybrmst.dicodingstory.ui.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.ybrmst.dicodingstory.ui.theme.DicodingStoryTheme

@Composable
fun OnboardingScreen(
  modifier: Modifier = Modifier,
  onNavigateToSignIn: () -> Unit,
  onNavigateToSignUp: () -> Unit,
) {
  Scaffold { innerPadding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(text = "Onboarding Screen")
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardingScreenPreview() {
  DicodingStoryTheme {
    OnboardingScreen(
      onNavigateToSignIn = {},
      onNavigateToSignUp = {}
    )
  }
}