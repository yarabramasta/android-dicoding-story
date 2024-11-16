package dev.ybrmst.dicodingstory.domain.business.auth

import androidx.compose.runtime.Immutable
import dev.ybrmst.dicodingstory.di.IoDispatcher
import dev.ybrmst.dicodingstory.domain.business.common.UseCase
import dev.ybrmst.dicodingstory.domain.models.User
import dev.ybrmst.dicodingstory.domain.repositories.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SignInUseCase @Inject constructor(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val repo: AuthRepository,
) : UseCase<SignInUseCase.SignInParams, User>(dispatcher) {

  @Immutable
  inner class SignInParams(
    val email: String,
    val password: String,
  )

  override suspend fun execute(params: SignInParams) = repo.login(
    params.email,
    params.password
  )
}