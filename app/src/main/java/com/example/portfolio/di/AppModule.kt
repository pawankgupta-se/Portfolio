package com.example.portfolio.di

import com.example.portfolio.data.repositories.UserRepositoryImpl
import com.example.portfolio.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    fun bindsUserRepository(userRepository: UserRepositoryImpl): UserRepository
}