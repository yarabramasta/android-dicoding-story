package dev.ybrmst.dicodingstory.domain.business.common

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

abstract class FlowUseCase<in Params, Success>(
  private val dispatcher: CoroutineDispatcher,
) {

  operator fun invoke(
    params: Params,
  ): Flow<Result<Success>> = execute(params)
    .catch { ex ->
      Log.e(
        "FlowUseCase",
        "An error occurred while executing use case.",
        ex
      )
      emit(Result.failure(ex))
    }
    .flowOn(dispatcher)


  abstract fun execute(params: Params): Flow<Result<Success>>
}