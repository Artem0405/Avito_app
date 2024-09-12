package com.example.app_avito.data.repository

import com.example.app_avito.data.api.ApiService
import com.example.app_avito.data.model.Product
import com.example.app_avito.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getProducts(
        category: String? = null,
        sort: String? = null,
        limit: Int? = null,
        page: Int? = null
    ): Flow<Resource<List<Product>>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = apiService.getProducts(category, sort, limit, page)
                if (response.isSuccessful) {
                    val products = response.body()
                    if (products != null) {
                        emit(Resource.Success(products))
                    } else {
                        emit(Resource.Success(emptyList())) // Или Resource.Error("Products not found")
                    }
                } else {
                    emit(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getProductById(id: Int): Flow<Resource<Product>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = apiService.getProductById(id)
                if (response.isSuccessful) {
                    val product = response.body()
                    if (product != null) {
                        emit(Resource.Success(product))
                    } else {
                        emit(Resource.Error("Product not found"))
                    }
                } else {
                    emit(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)
    }
}