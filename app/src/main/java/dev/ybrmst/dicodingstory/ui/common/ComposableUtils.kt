package dev.ybrmst.dicodingstory.ui.common

import android.content.Context
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.scopedViewModel(
  navController: NavController,
): T {
  val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
  val parentEntry: NavBackStackEntry = remember(this) {
    navController.getBackStackEntry(navGraphRoute)
  }
  return hiltViewModel(parentEntry)
}

@Composable
fun keyboardAsState(): State<Boolean> {
  val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
  return rememberUpdatedState(isImeVisible)
}

fun getLocalizedString(context: Context, strResId: String): String {
  val resId = strResId.toIntOrNull() ?: return ""
  return context.getString(resId)
}