package dev.ybrmst.dicodingstory.domain.business.auth

import dev.ybrmst.dicodingstory.di.IoDispatcher
import dev.ybrmst.dicodingstory.domain.business.common.UseCase
import dev.ybrmst.dicodingstory.domain.models.User
import dev.ybrmst.dicodingstory.domain.repositories.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetCurrentUserUseCase(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val repo: AuthRepository,
) : UseCase<Unit, User?>(dispatcher) {

  override suspend fun execute(
    params: Unit,
  ): Result<User?> = Result.success(repo.getUser().getOrNull())
}