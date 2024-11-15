package dev.ybrmst.dicodingstory.data.network.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
  val error: Boolean,
  val message: String,
)