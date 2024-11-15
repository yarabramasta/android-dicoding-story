package dev.ybrmst.dicodingstory.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
  val id: String,
  val name: String,
)
