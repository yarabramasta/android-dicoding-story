package dev.ybrmst.dicodingstory.data.repositories

import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import dev.ybrmst.dicodingstory.data.local.auth.dao.SessionDao
import dev.ybrmst.dicodingstory.data.local.auth.entities.SessionEntity
import dev.ybrmst.dicodingstory.data.network.auth.services.AuthService
import dev.ybrmst.dicodingstory.domain.errors.AuthError
import dev.ybrmst.dicodingstory.domain.models.User
import dev.ybrmst.dicodingstory.domain.repositories.AuthRepository
import dev.ybrmst.dicodingstory.domain.repositories.PreferencesRepository
import kotlinx.coroutines.flow.lastOrNull
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
  private val authService: AuthService,
  private val prefsRepo: PreferencesRepository,
  private val sessionDao: SessionDao,
) : AuthRepository {

  override suspend fun getUser(): User? {
    prefsRepo.getAuthToken() ?: return null

    val session = sessionDao.getSessions().lastOrNull()?.firstOrNull()
    if (session == null) {
      prefsRepo.revokeAuthToken()
      return null
    }

    val user = User(session.userId, session.name)
    return user
  }

  override suspend fun login(
    email: String,
    password: String,
  ): Pair<User?, AuthError?> {
    var result: Pair<User?, AuthError?> = null to AuthError.BadRequestLogin()

    authService.login(email, password)
      .suspendOnSuccess {
        val user = User(data.loginResult.userID, data.loginResult.name)
        val session = SessionEntity(userId = user.id, name = user.name)

        prefsRepo.setAuthToken(data.loginResult.token)
        sessionDao.saveSession(session)

        result = user to null
      }
      .suspendOnError {
        val statusCode = (payload as? Response<*>)?.code() ?: 500
        result = when (statusCode) {
          400 -> null to AuthError.InvalidCredentials()
          else -> result
        }
      }

    return result
  }

  override suspend fun register(
    name: String,
    email: String,
    password: String,
  ): Pair<Unit, AuthError?> {
    var result: Pair<Unit, AuthError?> = Unit to AuthError.BadRequestRegister()

    authService.register(name, email, password)
      .suspendOnSuccess { result = Unit to null }
      .suspendOnError {
        val statusCode = (payload as? Response<*>)?.code() ?: 500
        result = when (statusCode) {
          400 -> Unit to AuthError.DuplicatedCredentials()
          else -> result
        }
      }

    return result
  }

  override suspend fun logout(): Pair<Unit, AuthError?> {
    prefsRepo.revokeAuthToken()
    sessionDao.revokeSessions()
    return Unit to null
  }
}