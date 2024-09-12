package com.example.app_avito.di

import com.example.app_avito.data.repository.ProductRepository
import com.example.app_avito.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(productRepository: ProductRepository): ProductRepository {
        return productRepository
    }

    @Provides
    @Singleton
    fun provideUserRepository(userRepository: UserRepository): UserRepository {
        return userRepository
    }
}