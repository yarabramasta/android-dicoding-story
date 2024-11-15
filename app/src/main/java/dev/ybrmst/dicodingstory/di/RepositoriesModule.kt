package dev.ybrmst.dicodingstory.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ybrmst.dicodingstory.data.repositories.AuthRepositoryImpl
import dev.ybrmst.dicodingstory.data.repositories.PreferencesRepositoryImpl
import dev.ybrmst.dicodingstory.domain.repositories.AuthRepository
import dev.ybrmst.dicodingstory.domain.repositories.PreferencesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

  @Binds
  @Singleton
  abstract fun bindPreferencesRepository(
    preferencesRepositoryImpl: PreferencesRepositoryImpl,
  ): PreferencesRepository

  @Binds
  @Singleton
  abstract fun bindAuthRepository(
    authRepositoryImpl: AuthRepositoryImpl,
  ): AuthRepository
}