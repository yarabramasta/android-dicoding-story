package dev.ybrmst.dicodingstory.domain.errors

sealed class AuthError : Throwable() {
  data class Unauthorized(
    override val message: String = "Uh oh! Seems like you're not logged in.",
  ) : AuthError()

  data class BadRequestLogin(
    override val message: String = "An error occurred while logging in.",
  ) : AuthError()

  data class BadRequestRegister(
    override val message: String = "An error occurred while creating account.",
  ) : AuthError()

  data class InvalidCredentials(
    override val message: String = "Invalid email or password.",
  ) : AuthError()

  data class DuplicatedCredentials(
    override val message: String = "Email is already in use.",
  ) : AuthError()
}