package dev.ybrmst.dicodingstory.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.ybrmst.dicodingstory.BuildConfig
import dev.ybrmst.dicodingstory.data.local.auth.dao.SessionDao
import dev.ybrmst.dicodingstory.data.local.db.DicodingStoryDatabase
import javax.inject.Singleton

val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(
  name = BuildConfig.APPLICATION_ID + ".preferences.auth"
)

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

  @Provides
  @Singleton
  fun provideAuthDataStore(
    @ApplicationContext context: Context,
  ): DataStore<Preferences> = context.authDataStore

  @Provides
  @Singleton
  fun provideDicodingStoryDatabase(
    @ApplicationContext context: Context,
  ): DicodingStoryDatabase = Room.databaseBuilder(
    context,
    DicodingStoryDatabase::class.java,
    "dicoding_story.db"
  ).build()

  @Provides
  @Singleton
  fun provideSessionDao(
    dicodingStoryDatabase: DicodingStoryDatabase,
  ): SessionDao = dicodingStoryDatabase.sessionDao()
}