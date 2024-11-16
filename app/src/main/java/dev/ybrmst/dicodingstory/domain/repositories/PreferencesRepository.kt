package dev.ybrmst.dicodingstory.domain.repositories

interface PreferencesRepository {
  suspend fun getAuthToken(): String?

  suspend fun setAuthToken(token: String): Boolean

  suspend fun revokeAuthToken(): Boolean
}