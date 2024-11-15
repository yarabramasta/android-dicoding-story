package dev.ybrmst.dicodingstory.domain.repositories

interface PreferencesRepository {
  suspend fun getAuthToken(): Result<String>

  suspend fun setAuthToken(token: String): Result<Boolean>
}