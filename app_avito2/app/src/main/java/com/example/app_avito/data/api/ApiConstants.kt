package com.example.app_avito.data.api

import com.example.app_avito.data.model.Product
import com.example.app_avito.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Регистрация пользователя
    @POST("app/v1/users")
    suspend fun registerUser(@Body user: User): Response<User>

    // Авторизация пользователя
    @POST("users/auth/login")
    suspend fun loginUser(@Body user: User): Response<LoginResponse>

    // Получение списка продуктов
    @GET("app/v1/products")
    suspend fun getProducts(
        @Query("category") category: String? = null,
        @Query("sort") sort: String? = null, // asc или desc для сортировки по цене
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null
    ): Response<List<Product>>

    // Получение информации о продукте по ID
    @GET("app/v1/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<Product>
}

data class LoginResponse(
    val token: String
)