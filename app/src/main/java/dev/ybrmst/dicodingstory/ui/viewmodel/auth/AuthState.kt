package dev.ybrmst.dicodingstory.ui.viewmodel.auth

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import dev.ybrmst.dicodingstory.domain.models.User
import dev.ybrmst.dicodingstory.ui.common.UiStatus
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class AuthState(
  val status: UiStatus,
  val user: User?,
) : Parcelable {
  companion object {
    fun initial() = AuthState(
      status = UiStatus.Loading,
      user = null
    )
  }
}