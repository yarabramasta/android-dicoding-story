package dev.ybrmst.dicodingstory.data.network.auth.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
  val error: Boolean,
  val message: String,
  val loginResult: LoginResult,
)

@Serializable
data class LoginResult(
  @SerialName("userId")
  val userID: String,
  val name: String,
  val token: String,
)