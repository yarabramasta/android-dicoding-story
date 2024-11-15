package dev.ybrmst.dicodingstory.domain.repositories

import dev.ybrmst.dicodingstory.domain.entities.User

interface AuthRepository {
  suspend fun getUser(): Result<User>

  suspend fun login(
    email: String,
    password: String,
  ): Result<User>

  suspend fun register(
    name: String,
    email: String,
    password: String,
  ): Result<Boolean>
}