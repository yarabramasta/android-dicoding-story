package dev.ybrmst.dicodingstory.domain.business.common

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<in Params, Data>(
  private val dispatcher: CoroutineDispatcher,
) {

  suspend operator fun invoke(params: Params): Pair<Data?, Throwable?> {
    return try {
      withContext(dispatcher) {
        execute(params)
      }
    } catch (ex: Throwable) {
      Log.e(
        "UseCase",
        "An error occurred while executing use case.",
        ex
      )
      null to ex
    }
  }

  protected abstract suspend fun execute(params: Params): Pair<Data?, Throwable?>
}