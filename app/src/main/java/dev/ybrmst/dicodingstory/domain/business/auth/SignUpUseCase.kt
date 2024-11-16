package dev.ybrmst.dicodingstory.domain.business.auth

import androidx.compose.runtime.Immutable
import dev.ybrmst.dicodingstory.di.IoDispatcher
import dev.ybrmst.dicodingstory.domain.business.common.UseCase
import dev.ybrmst.dicodingstory.domain.errors.AuthError
import dev.ybrmst.dicodingstory.domain.repositories.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val repo: AuthRepository,
) : UseCase<SignUpUseCase.SignUpParams, Boolean>(dispatcher) {

  @Immutable
  inner class SignUpParams(
    val email: String,
    val password: String,
    val name: String,
  )

  override suspend fun execute(
    params: SignUpParams,
  ): Pair<Boolean, AuthError?> = repo.register(
    params.email,
    params.password,
    params.name
  ).let { (_, ex) -> false to ex }
}