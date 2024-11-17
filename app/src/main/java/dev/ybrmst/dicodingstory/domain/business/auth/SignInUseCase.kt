package dev.ybrmst.dicodingstory.domain.business.auth

import androidx.compose.runtime.Immutable
import dev.ybrmst.dicodingstory.di.IoDispatcher
import dev.ybrmst.dicodingstory.domain.business.common.UseCase
import dev.ybrmst.dicodingstory.domain.models.User
import dev.ybrmst.dicodingstory.domain.repositories.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@Immutable
class SignInParams(
  val email: String,
  val password: String,
)

class SignInUseCase @Inject constructor(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val repo: AuthRepository,
) : UseCase<SignInParams, User>(dispatcher) {
  override suspend fun execute(params: SignInParams) = repo.login(
    params.email,
    params.password
  )
}