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

  override suspend fun getUser(): Result<User> {
    prefsRepo
      .getAuthToken()
      .getOrNull()
      ?: return Result.failure(AuthError.Unauthorized())

    val session = sessionDao.getSessions().lastOrNull()?.firstOrNull()
    if (session == null) {
      prefsRepo.revokeAuthToken()
      return Result.failure(AuthError.Unauthorized())
    }

    val user = User(session.userId, session.name)
    return Result.success(user)
  }

  override suspend fun login(
    email: String,
    password: String,
  ): Result<User> {
    var result: Result<User> = Result.failure(AuthError.BadRequestLogin())

    authService.login(email, password)
      .suspendOnSuccess {
        val user = User(data.loginResult.userID, data.loginResult.name)
        val session = SessionEntity(userId = user.id, name = user.name)

        prefsRepo.setAuthToken(data.loginResult.token)
        sessionDao.saveSession(session)

        result = Result.success(user)
      }
      .suspendOnError {
        val statusCode = (payload as? Response<*>)?.code() ?: 500
        result = when (statusCode) {
          400 -> Result.failure(AuthError.InvalidCredentials())
          else -> result
        }
      }

    return result
  }

  override suspend fun register(
    name: String,
    email: String,
    password: String,
  ): Result<Boolean> {
    var result: Result<Boolean> = Result.failure(AuthError.BadRequestRegister())

    authService.register(name, email, password)
      .suspendOnSuccess { result = Result.success(true) }
      .suspendOnError {
        val statusCode = (payload as? Response<*>)?.code() ?: 500
        result = when (statusCode) {
          400 -> Result.failure(AuthError.DuplicatedCredentials())
          else -> result
        }
      }

    return result
  }

  override suspend fun logout(): Result<Boolean> {
    prefsRepo.revokeAuthToken()
    sessionDao.revokeSessions()
    return Result.success(true)
  }
}