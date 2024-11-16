package dev.ybrmst.dicodingstory.ui.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.ybrmst.dicodingstory.ui.theme.DicodingStoryTheme

@Composable
fun IndexScreen(
  modifier: Modifier = Modifier,
) {
  IndexScreenContent(modifier = modifier)
}

@Composable
private fun IndexScreenContent(
  modifier: Modifier = Modifier,
) {
  Scaffold(
    modifier = modifier.fillMaxSize()
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      CircularProgressIndicator()
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun IndexScreenPreview() {
  DicodingStoryTheme {
    IndexScreenContent()
  }
}