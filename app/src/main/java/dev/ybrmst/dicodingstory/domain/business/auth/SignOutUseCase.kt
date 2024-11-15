package dev.ybrmst.dicodingstory.domain.business.auth

import dev.ybrmst.dicodingstory.di.IoDispatcher
import dev.ybrmst.dicodingstory.domain.business.common.UseCase
import dev.ybrmst.dicodingstory.domain.repositories.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher

class SignOutUseCase(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val repo: AuthRepository,
) : UseCase<Unit, Unit>(dispatcher) {

  override suspend fun execute(
    params: Unit,
  ): Result<Unit> = repo.logout()
    .fold(
      onSuccess = { Result.success(Unit) },
      onFailure = { Result.failure(it) }
    )
}