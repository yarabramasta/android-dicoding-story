package dev.ybrmst.dicodingstory.lib.http.interceptors

import dev.ybrmst.dicodingstory.di.IoDispatcher
import dev.ybrmst.dicodingstory.domain.repositories.PreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val repo: PreferencesRepository,
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val token: String? = runBlocking(
      context = dispatcher
    ) { repo.getAuthToken() }

    var request = chain.request()
    if (token != null) {
      request = request.newBuilder()
        .addHeader("Authorization", "Bearer $token")
        .build()
    }

    return chain.proceed(request)
  }
}