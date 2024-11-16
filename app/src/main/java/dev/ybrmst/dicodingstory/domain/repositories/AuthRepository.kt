package dev.ybrmst.dicodingstory.domain.repositories

import dev.ybrmst.dicodingstory.domain.errors.AuthError
import dev.ybrmst.dicodingstory.domain.models.User

interface AuthRepository {
  suspend fun getUser(): User?

  suspend fun login(
    email: String,
    password: String,
  ): Pair<User?, AuthError?>

  suspend fun register(
    name: String,
    email: String,
    password: String,
  ): Pair<Unit, AuthError?>

  suspend fun logout(): Pair<Unit, AuthError?>
}