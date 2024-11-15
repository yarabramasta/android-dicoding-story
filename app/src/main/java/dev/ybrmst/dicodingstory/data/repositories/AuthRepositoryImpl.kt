package dev.ybrmst.dicodingstory.data.repositories

import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import dev.ybrmst.dicodingstory.data.network.auth.services.AuthService
import dev.ybrmst.dicodingstory.domain.entities.User
import dev.ybrmst.dicodingstory.domain.repositories.AuthRepository
import dev.ybrmst.dicodingstory.domain.repositories.PreferencesRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
  private val authService: AuthService,
  private val prefsRepo: PreferencesRepository,
) : AuthRepository {

  override suspend fun getUser(): Result<User> {
//    prefsRepo
//      .getAuthToken()
//      .getOrNull()
//      ?: return Result.failure(Throwable("User is not authenticated."))

    throw NotImplementedError("Fetching user data is not implemented.")
  }

  override suspend fun login(email: String, password: String): Result<User> {
    var result: Result<User> = Result.failure(
      Throwable("An error occurred while logging in.")
    )

    authService.login(email, password)
      .suspendOnSuccess {
        prefsRepo.setAuthToken(data.loginResult.token)
        val user = User(
          data.loginResult.userID,
          data.loginResult.name
        )
        result = Result.success(user)
      }
      .suspendOnError {
        val statusCode = (payload as? Response<*>)?.code() ?: 500
        result = when (statusCode) {
          400 -> Result.failure(Throwable("Invalid email or password."))
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
    var result: Result<Boolean> = Result.failure(
      Throwable("An error occurred while creating account.")
    )

    authService.register(name, email, password)
      .suspendOnSuccess { result = Result.success(true) }
      .suspendOnError {
        val statusCode = (payload as? Response<*>)?.code() ?: 500
        result = when (statusCode) {
          400 -> Result.failure(Throwable("Email is already in use."))
          else -> result
        }
      }

    return result
  }
}