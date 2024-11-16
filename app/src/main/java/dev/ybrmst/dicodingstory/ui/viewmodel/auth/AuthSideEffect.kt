package dev.ybrmst.dicodingstory.ui.viewmodel.auth

import dev.ybrmst.dicodingstory.ui.composables.screens.RootRoute

sealed class AuthSideEffect {
  data class NavigateToNext(val route: RootRoute) : AuthSideEffect()
}