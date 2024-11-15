package dev.ybrmst.dicodingstory.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.*
import dev.ybrmst.dicodingstory.data.local.preferences.PreferencesKeys
import dev.ybrmst.dicodingstory.domain.repositories.PreferencesRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
  private val authDataStore: DataStore<Preferences>,
) : PreferencesRepository {

  override suspend fun getAuthToken(): Result<String> {
    return Result.runCatching {
      authDataStore.data
        .catch { ex ->
          if (ex is IOException) emit(emptyPreferences())
          else throw ex
        }
        .map { it[PreferencesKeys.AUTH_TOKEN_PREFERENCES_KEY] ?: "" }
        .first()
    }
  }

  override suspend fun setAuthToken(token: String): Result<Boolean> {
    return try {
      authDataStore.edit {
        it[PreferencesKeys.AUTH_TOKEN_PREFERENCES_KEY] = token
      }
      Result.success(true)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}