package dev.ybrmst.dicodingstory.ui.viewmodel.signup

import android.util.Patterns
import androidx.compose.runtime.Immutable
import dev.ybrmst.dicodingstory.R

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
    if (email.isEmpty()) return R.string.err_form_email_empty.toString()
    if (
      Patterns.EMAIL_ADDRESS
        .matcher(email)
        .matches()
    ) return R.string.err_form_email_invalid.toString()
    return ""
  }

  fun validatePassword(): String {
    if (password.isEmpty()) return R.string.err_form_pass_empty.toString()
    if (password.length < 8) return R.string.err_form_pass_invalid.toString()
    return ""
  }

  fun validateName(): String {
    if (name.isEmpty()) return R.string.err_form_name_empty.toString()
    if (name.length < 2) return R.string.err_form_name_invalid.toString()
    return ""
  }

  val isValid: Boolean
    get() = validateEmail().isEmpty()
      && validatePassword().isEmpty()
      && validateName().isEmpty()
}