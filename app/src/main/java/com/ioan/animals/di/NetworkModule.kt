package com.ioan.animals.di


import com.ioan.animals.main.AuthInterceptor
import com.ioan.animals.main.RefreshTokenClient
import com.google.gson.GsonBuilder
import com.ioan.animals.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(apiSerice: RefreshTokenClient): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(AuthInterceptor(apiSerice))

        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideRefreshTokenClient(): RefreshTokenClient {

        val gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(RefreshTokenClient::class.java)
    }
}