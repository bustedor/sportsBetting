package com.example.sportsbetting.detail.di

import com.example.sportsbetting.detail.data.DetailRepositoryImpl
import com.example.sportsbetting.detail.data.DetailService
import com.example.sportsbetting.detail.domain.DetailRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class DetailModule {

    @Binds
    @ViewModelScoped
    abstract fun provideDetailRepository(
        repositoryImpl: DetailRepositoryImpl
    ): DetailRepository

    companion object {

        @Provides
        @ViewModelScoped
        fun provideDetailService(retrofit: Retrofit): DetailService {
            return retrofit.create(DetailService::class.java)
        }

    }

}
