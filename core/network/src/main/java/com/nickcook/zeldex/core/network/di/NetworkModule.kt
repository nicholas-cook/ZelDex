package com.nickcook.zeldex.core.network.di

import com.nickcook.zeldex.core.network.BuildConfig
import com.nickcook.zeldex.core.network.services.HyruleCompendiumService
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl("https://botw-compendium.herokuapp.com/api/v3/compendium/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideHyruleCompendiumService(retrofit: Retrofit): HyruleCompendiumService {
        return retrofit.create(HyruleCompendiumService::class.java)
    }
}
