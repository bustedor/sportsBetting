package com.example.sportsbetting.bulletin.di

import com.example.sportsbetting.bulletin.data.BulletinRepositoryImpl
import com.example.sportsbetting.bulletin.data.BulletinService
import com.example.sportsbetting.bulletin.domain.BulletinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class BulletinModule {

    @Binds
    @ViewModelScoped
    abstract fun provideBulletinRepository(
        repositoryImpl: BulletinRepositoryImpl
    ): BulletinRepository

    companion object {

        @Provides
        @ViewModelScoped
        fun provideBulletinService(retrofit: Retrofit): BulletinService {
            return retrofit.create(BulletinService::class.java)
        }

    }

}
