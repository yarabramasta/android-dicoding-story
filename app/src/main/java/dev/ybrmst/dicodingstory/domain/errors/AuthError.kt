package dev.ybrmst.dicodingstory.domain.errors

import dev.ybrmst.dicodingstory.R

sealed class AuthError : Throwable() {
  @Suppress("unused")
  data class Unauthorized(
    override val message: String = R.string.err_unauthorized.toString(),
  ) : AuthError()

  data class BadRequestSignIn(
    override val message: String = R.string.err_bad_req_signin.toString(),
  ) : AuthError()

  data class BadRequestSignUp(
    override val message: String = R.string.err_bad_req_signup.toString(),
  ) : AuthError()

  data class InvalidCredentials(
    override val message: String = R.string.err_invalid_creds.toString(),
  ) : AuthError()

  data class DuplicatedCredentials(
    override val message: String = R.string.err_duplicated_creds.toString(),
  ) : AuthError()
}