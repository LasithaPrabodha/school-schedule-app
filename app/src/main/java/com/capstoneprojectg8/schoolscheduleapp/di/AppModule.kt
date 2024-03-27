package com.capstoneprojectg8.schoolscheduleapp.di

import com.capstoneprojectg8.schoolscheduleapp.AppDispatchers
import com.capstoneprojectg8.schoolscheduleapp.AppDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


@Retention(AnnotationRetention.BINARY)
@Qualifier
@MustBeDocumented
annotation class AppCoroutineScope

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun appDispatchers(impl: AppDispatchersImpl): AppDispatchers
    companion object {
        @Provides
        @Singleton
        @AppCoroutineScope
        fun appScope(appDispatchers: AppDispatchers) =
            CoroutineScope(appDispatchers.io + SupervisorJob())
    }
}