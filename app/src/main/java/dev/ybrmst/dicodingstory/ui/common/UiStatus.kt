package dev.ybrmst.dicodingstory.ui.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class UiStatus : Parcelable {

  @Parcelize
  data object Loading : UiStatus()

  @Parcelize
  data object Success : UiStatus()

  @Parcelize
  data class Failed(val message: String = "") : UiStatus()
}