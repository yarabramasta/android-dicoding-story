package dev.ybrmst.dicodingstory.ui.viewmodel.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ybrmst.dicodingstory.domain.business.auth.GetCurrentUserUseCase
import dev.ybrmst.dicodingstory.domain.models.User
import dev.ybrmst.dicodingstory.ui.common.UiStatus
import dev.ybrmst.dicodingstory.ui.composables.screens.RootRoute
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class AuthViewModel(
  private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : ContainerHost<AuthState, AuthSideEffect>, ViewModel() {

  override val container = container<AuthState, AuthSideEffect>(
    AuthState.initial()
  )

  init {
    intent {
      fetchCurrentUser()
    }
  }


  /**
   * WARNING: Should only be used in another view model e.g. [LoginViewModel]
   */
  fun update(user: User?) {
    intent {
      reduce { state.copy(user = user) }
    }
  }

  private fun fetchCurrentUser() {
    intent {
      reduce { state.copy(status = UiStatus.Loading) }
      getCurrentUserUseCase(Unit)
        .fold(
          onFailure = {
            val message = it.message ?: "Something went wrong..."
            reduce {
              state.copy(
                status = UiStatus.Failed(message),
                user = null
              )
            }
            postSideEffect(AuthSideEffect.ShowToast(message))
          },
          onSuccess = { currentUser ->
            reduce { state.copy(status = UiStatus.Success, user = currentUser) }
            postSideEffect(
              AuthSideEffect.NavigateToNext(
                if (currentUser != null) RootRoute.Home
                else RootRoute.Onboarding
              )
            )
          }
        )
    }
  }
}