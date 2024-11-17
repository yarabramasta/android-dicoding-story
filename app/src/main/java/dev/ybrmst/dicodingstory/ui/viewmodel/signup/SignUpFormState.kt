package dev.ybrmst.dicodingstory.ui.viewmodel.signup

import android.util.Patterns
import androidx.compose.runtime.Immutable

@Immutable
data class SignUpFormState(
  val email: String,
  val password: String,
  val name: String,
  val emailError: String,
  val passwordError: String,
  val nameError: String,
) {
  companion object {
    fun initial() = SignUpFormState(
      email = "",
      password = "",
      name = "",
      emailError = "",
      passwordError = "",
      nameError = "",
    )
  }

  fun validateEmail(): String {
    if (email.isEmpty()) return "Email must not be empty."
    if (
      Patterns.EMAIL_ADDRESS
        .matcher(email)
        .matches()
    ) return "Please use a valid email address."
    return ""
  }

  fun validatePassword(): String {
    if (password.isEmpty()) return "Password must not be empty."
    if (password.length < 8) return "Password must be at least 8 characters."
    return ""
  }

  fun validateName(): String {
    if (name.isEmpty()) return "Name must not be empty."
    if (name.length < 2) return "Name must be at least 2 characters."
    return ""
  }

  val isValid: Boolean
    get() = validateEmail().isEmpty()
      && validatePassword().isEmpty()
      && validateName().isEmpty()
}