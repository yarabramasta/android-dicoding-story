package dev.ybrmst.dicodingstory.di

import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ybrmst.dicodingstory.BuildConfig
import dev.ybrmst.dicodingstory.data.network.auth.services.AuthService
import dev.ybrmst.dicodingstory.domain.repositories.PreferencesRepository
import dev.ybrmst.dicodingstory.lib.http.interceptors.AuthTokenInterceptor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
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
  fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }
  }

  @Provides
  @Singleton
  fun provideAuthTokenInterceptor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    prefsRepo: PreferencesRepository,
  ): AuthTokenInterceptor = AuthTokenInterceptor(
    dispatcher = ioDispatcher,
    repo = prefsRepo
  )

  @Provides
  @Singleton
  fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    authTokenInterceptor: AuthTokenInterceptor,
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