package com.example.app_avito.data.repository

import android.content.Context
import com.example.app_avito.data.api.ApiService
import com.example.app_avito.data.model.User
import com.example.app_avito.utils.Resource
import com.example.app_avito.utils.SharedPreferencesManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) {

    private val sharedPreferencesManager = SharedPreferencesManager(context)

    suspend fun registerUser(user: User): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = apiService.registerUser(user)
                if (response.isSuccessful) {
                    val registeredUser = response.body()
                    if (registeredUser != null) {
                        emit(Resource.Success(registeredUser))
                    } else {
                        emit(Resource.Error("Registration failed: User data is null"))
                    }
                } else {
                    emit(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun loginUser(user: User): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = apiService.loginUser(user)
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (token != null) {
                        sharedPreferencesManager.saveToken(token)
                        emit(Resource.Success(token))
                    } else {
                        emit(Resource.Error("Token is null"))
                    }
                } else {
                    emit(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferencesManager.getToken() != null
    }

    fun logout() {
        sharedPreferencesManager.clearToken()
    }

    fun getToken(): String? {
        return sharedPreferencesManager.getToken()
    }
}