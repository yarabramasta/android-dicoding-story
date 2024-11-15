package dev.ybrmst.dicodingstory.data.network.auth.services

import com.skydoves.sandwich.ApiResponse
import dev.ybrmst.dicodingstory.data.network.auth.models.LoginResponse
import dev.ybrmst.dicodingstory.data.network.auth.models.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.POST

interface AuthService {

  @POST("register")
  suspend fun register(
    @Field("name") name: String,
    @Field("email") email: String,
    @Field("password") password: String,
  ): ApiResponse<RegisterResponse>

  @POST("login")
  suspend fun login(
    @Field("email") email: String,
    @Field("password") password: String,
  ): ApiResponse<LoginResponse>
}