package dev.ybrmst.dicodingstory.ui.composables.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.ybrmst.dicodingstory.ui.common.keyboardAsState
import dev.ybrmst.dicodingstory.ui.theme.DicodingStoryTheme

@Composable
fun SignInScreen(
  modifier: Modifier = Modifier,
  onBack: () -> Unit,
) {
  val isKeyboardOpen by keyboardAsState()
  BackHandler(enabled = !isKeyboardOpen) {
    onBack()
  }

  Scaffold { innerPadding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(text = "SignIn Screen")
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignInScreenPreview() {
  DicodingStoryTheme {
    SignInScreen(
      onBack = {}
    )
  }
}