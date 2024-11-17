package dev.ybrmst.dicodingstory.ui.viewmodel.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ybrmst.dicodingstory.domain.business.auth.SignInParams
import dev.ybrmst.dicodingstory.domain.business.auth.SignInUseCase
import dev.ybrmst.dicodingstory.ui.common.UiStatus
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
  private val signInUseCase: SignInUseCase,
) : ViewModel(),
  ContainerHost<SignUpState, SignUpSideEffect> {

  override val container =
    viewModelScope.container<SignUpState, SignUpSideEffect>(
      SignUpState.initial()
    )

  fun onNameChanged(name: String) {
    intent {
      reduce {
        state.copy(
          form = state.form.copy(name = name)
        )
      }
    }
  }

  fun onEmailChanged(email: String) {
    intent {
      reduce {
        state.copy(
          form = state.form.copy(email = email)
        )
      }
    }
  }

  fun onPasswordChanged(password: String) {
    intent {
      reduce {
        state.copy(
          form = state.form.copy(password = password)
        )
      }
    }
  }

  private fun validateForm() {
    intent {
      reduce {
        state.copy(
          form = state.form.copy(
            emailError = state.form.validateEmail(),
            passwordError = state.form.validatePassword(),
            nameError = state.form.validateName()
          )
        )
      }
    }
  }

  fun onSubmit() {
    validateForm()

    intent {
      if (state.form.isValid) {
        reduce { state.copy(status = UiStatus.Loading) }

        container.scope.launch {
          signInUseCase(
            SignInParams(
              email = state.form.email,
              password = state.form.password
            )
          ).fold(
            onSuccess = {
              reduce {
                state.copy(
                  status = UiStatus.Success,
                  form = SignUpFormState.initial()
                )
              }
              postSideEffect(SignUpSideEffect.OnSuccessNavigate)
              postSideEffect(
                SignUpSideEffect.ShowMessage(
                  "Your account successfully created. Please sign in to continue."
                )
              )
            },
            onFailure = {
              val message =
                it.message ?: "Failed to create account. Please try again."
              reduce { state.copy(status = UiStatus.Failed()) }
              postSideEffect(SignUpSideEffect.ShowMessage(message))
            }
          )
        }
      }
    }
  }
}