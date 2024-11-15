package dev.ybrmst.dicodingstory.ui.viewmodel.auth

import androidx.compose.runtime.Immutable
import dev.ybrmst.dicodingstory.domain.models.User
import dev.ybrmst.dicodingstory.ui.common.UiStatus

@Immutable
data class AuthState(
  val status: UiStatus,
  val user: User?,
) {
  companion object {
    fun initial() = AuthState(
      status = UiStatus.Loading,
      user = null
    )
  }
}