package com.android.mandi.modules

import com.android.mandi.BuildConfig
import com.android.mandi.constants.BASE_URL
import com.android.mandi.constants.REQUEST_TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    
    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().setLenient().create()
    
    @Provides
    @Singleton
    fun provideConverterFactory(gson : Gson) : GsonConverterFactory = GsonConverterFactory.create(gson)
    
    @Provides
    @Singleton
    fun provideInterceptor() : Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val requestBuilder = request.newBuilder()
            requestBuilder.method(request.method(), request.body())
            request = requestBuilder.build()
            chain.proceed(request)
        }
    }
    
    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor : Interceptor) : OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .addInterceptor(interceptor)
        .build()
    
    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient : OkHttpClient, converterFactory : GsonConverterFactory) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

}