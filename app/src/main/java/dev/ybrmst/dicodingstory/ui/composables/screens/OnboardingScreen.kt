package dev.ybrmst.dicodingstory.ui.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
      Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
      ) {
        Button(
          onClick = onNavigateToSignUp,
        ) {
          Text(text = "Get Started")
        }
        OutlinedButton(
          onClick = onNavigateToSignIn
        ) {
          Text(text = "Sign In")
        }
      }
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