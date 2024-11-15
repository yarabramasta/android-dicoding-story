package dev.ybrmst.dicodingstory.di

import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ybrmst.dicodingstory.BuildConfig
import dev.ybrmst.dicodingstory.data.network.auth.services.AuthService
import dev.ybrmst.dicodingstory.domain.repositories.PreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDataModule {

  @Provides
  @Singleton
  fun provideLoggingInterceptor(): Interceptor {
    return HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }
  }

  @Provides
  @Singleton
  fun provideAuthTokenInterceptor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    prefsRepo: PreferencesRepository,
  ): Interceptor {
    return Interceptor { chain ->
      val token: String? = runBlocking(
        context = ioDispatcher
      ) { prefsRepo.getAuthToken().getOrNull() }

      var request = chain.request()
      if (token != null) {
        request = request.newBuilder()
          .addHeader("Authorization", "Bearer $token")
          .build()
      }

      chain.proceed(request)
    }
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(
    loggingInterceptor: Interceptor,
    authTokenInterceptor: Interceptor,
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .connectTimeout(10, TimeUnit.SECONDS)
      .addInterceptor(loggingInterceptor)
      .addInterceptor(authTokenInterceptor)
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(
    okHttpClient: OkHttpClient,
  ): Retrofit {
    val mimetype = "application/json; charset=UTF8".toMediaType()
    return Retrofit.Builder()
      .baseUrl(BuildConfig.API_BASE_URL)
      .addConverterFactory(Json.asConverterFactory(mimetype))
      .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
      .client(okHttpClient)
      .build()
  }

  @Provides
  @Singleton
  fun provideAuthService(
    retrofit: Retrofit,
  ): AuthService = retrofit.create(AuthService::class.java)
}