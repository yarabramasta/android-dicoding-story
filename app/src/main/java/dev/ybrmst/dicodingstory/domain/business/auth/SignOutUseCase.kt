package dev.ybrmst.dicodingstory.domain.business.auth

import dev.ybrmst.dicodingstory.di.IoDispatcher
import dev.ybrmst.dicodingstory.domain.business.common.UseCase
import dev.ybrmst.dicodingstory.domain.repositories.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val repo: AuthRepository,
) : UseCase<Unit, Boolean>(dispatcher) {

  override suspend fun execute(params: Unit) = repo.logout()
    .let { (_, ex) -> true to ex }
}