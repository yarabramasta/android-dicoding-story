package dev.ybrmst.dicodingstory.ui.composables.screens

import kotlinx.serialization.Serializable

@Serializable
sealed class RootRoute {

  @Serializable
  data object Index : RootRoute()

  @Serializable
  data object Onboarding : RootRoute()

  @Serializable
  data object SignIn : RootRoute()

  @Serializable
  data object SignUp : RootRoute()

  @Serializable
  data object Home : RootRoute()
}