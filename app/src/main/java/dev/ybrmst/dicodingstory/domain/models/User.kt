package dev.ybrmst.dicodingstory.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class User(
  val id: String,
  val name: String,
) : Parcelable