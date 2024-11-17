package dev.ybrmst.dicodingstory.ui.viewmodel.signup

sealed class SignUpSideEffect {
  data class ShowMessage(val message: String) : SignUpSideEffect()
  data object OnSuccessNavigate : SignUpSideEffect()
}