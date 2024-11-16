package dev.ybrmst.dicodingstory.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ybrmst.dicodingstory.domain.business.auth.*
import dev.ybrmst.dicodingstory.domain.repositories.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

  @Provides
  @Singleton
  fun provideSignInUseCase(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    authRepository: AuthRepository,
  ): SignInUseCase = SignInUseCase(
    dispatcher = ioDispatcher,
    repo = authRepository
  )

  @Provides
  @Singleton
  fun provideSignUpUseCase(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    authRepository: AuthRepository,
  ): SignUpUseCase = SignUpUseCase(
    dispatcher = ioDispatcher,
    repo = authRepository
  )

  @Provides
  @Singleton
  fun provideSignOutUseCase(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    authRepository: AuthRepository,
  ): SignOutUseCase = SignOutUseCase(
    dispatcher = ioDispatcher,
    repo = authRepository
  )

  @Provides
  @Singleton
  fun provideGetUserUseCase(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    authRepository: AuthRepository,
  ): GetUserUseCase = GetUserUseCase(
    dispatcher = ioDispatcher,
    repo = authRepository
  )
}