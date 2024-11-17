package dev.ybrmst.dicodingstory.ui.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ybrmst.dicodingstory.R
import dev.ybrmst.dicodingstory.domain.business.auth.GetUserUseCase
import dev.ybrmst.dicodingstory.domain.models.User
import dev.ybrmst.dicodingstory.ui.common.UiStatus
import dev.ybrmst.dicodingstory.ui.composables.screens.RootRoute
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
  private val getUserUseCase: GetUserUseCase,
) : ViewModel(), ContainerHost<AuthState, AuthSideEffect> {

  override val container = viewModelScope.container<AuthState, AuthSideEffect>(
    AuthState.initial(),
  ) {
    fetchUser()
  }

  fun update(user: User?) {
    intent {
      reduce { state.copy(user = user) }
    }
  }

  fun redirect(route: RootRoute) {
    intent {
      postSideEffect(AuthSideEffect.NavigateToNext(route))
    }
  }

  private fun fetchUser() {
    intent {
      reduce { state.copy(status = UiStatus.Loading) }
      container.scope.launch {
        getUserUseCase(Unit).fold(
          onFailure = { ex ->
            val message =
              ex.message ?: R.string.err_general_trouble.toString()
            reduce {
              state.copy(
                status = UiStatus.Failed(message),
                user = null
              )
            }
          },
          onSuccess = { user ->
            reduce {
              state.copy(
                status = UiStatus.Success,
                user = user
              )
            }
          }
        )
      }
    }
  }
}