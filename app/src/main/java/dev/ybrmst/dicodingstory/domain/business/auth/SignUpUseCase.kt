package dev.ybrmst.dicodingstory.domain.business.auth

import dev.ybrmst.dicodingstory.di.IoDispatcher
import dev.ybrmst.dicodingstory.domain.business.common.UseCase
import dev.ybrmst.dicodingstory.domain.repositories.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher

class SignUpUseCase(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val repo: AuthRepository,
) : UseCase<SignUpUseCase.SignUpParams, Unit>(dispatcher) {
  inner class SignUpParams(
    val email: String,
    val password: String,
    val name: String,
  )

  override suspend fun execute(
    params: SignUpParams,
  ): Result<Unit> = repo.register(
    params.email,
    params.password,
    params.name
  ).fold(
    onSuccess = { Result.success(Unit) },
    onFailure = { Result.failure(it) }
  )
}