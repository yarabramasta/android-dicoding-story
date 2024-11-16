package dev.ybrmst.dicodingstory.ui.viewmodel.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ybrmst.dicodingstory.domain.business.auth.GetUserUseCase
import dev.ybrmst.dicodingstory.domain.models.User
import dev.ybrmst.dicodingstory.ui.common.UiStatus
import dev.ybrmst.dicodingstory.ui.composables.screens.RootRoute
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val getUserUseCase: GetUserUseCase,
) : ViewModel(), ContainerHost<AuthState, AuthSideEffect> {

  override val container = container<AuthState, AuthSideEffect>(
    AuthState.initial(),
    savedStateHandle = savedStateHandle
  ) {
    fetchUser()
  }

  fun update(user: User?) = intent {
    reduce { state.copy(user = user) }
  }

  fun redirect(route: RootRoute) = intent {
    postSideEffect(AuthSideEffect.NavigateToNext(route))
  }

  private fun fetchUser() = intent(registerIdling = false) {
    reduce { state.copy(status = UiStatus.Loading) }
    val (user, ex) = getUserUseCase(Unit)

    if (ex != null) {
      reduce {
        state.copy(
          status = UiStatus.Failed(ex.message ?: "Something went wrong..."),
          user = null
        )
      }
      return@intent
    }

    reduce {
      state.copy(
        status = UiStatus.Success,
        user = user
      )
    }
  }
}