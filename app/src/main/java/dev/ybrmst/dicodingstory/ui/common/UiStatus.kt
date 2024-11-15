package dev.ybrmst.dicodingstory.ui.common

sealed class UiStatus {
  data object Loading : UiStatus()
  data object Success : UiStatus()
  data class Failed(val message: String = "") : UiStatus()
}