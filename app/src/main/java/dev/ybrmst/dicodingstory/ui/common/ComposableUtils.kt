package dev.ybrmst.dicodingstory.ui.common

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
  // if the destination route doesn't have a parent create a brand
  // new view model instance
  val navGraphRoute = destination.parent?.route ?: return hiltViewModel()

  // the destination does have a parent screen
  val parentEntry: NavBackStackEntry = remember(this) {
    navController.getBackStackEntry(navGraphRoute)
  }

  // return the view model associated with the parent destination
  return hiltViewModel(parentEntry)
}

@Composable
fun keyboardAsState(): State<Boolean> {
  val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
  return rememberUpdatedState(isImeVisible)
}