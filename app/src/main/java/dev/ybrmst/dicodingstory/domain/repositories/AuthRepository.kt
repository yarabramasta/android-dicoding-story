package dev.ybrmst.dicodingstory.domain.repositories

import dev.ybrmst.dicodingstory.domain.models.User

interface AuthRepository {
  suspend fun getUser(): Result<User?>

  suspend fun login(
    email: String,
    password: String,
  ): Result<User>

  suspend fun register(
    name: String,
    email: String,
    password: String,
  ): Result<Unit>

  suspend fun logout(): Result<Unit>
}