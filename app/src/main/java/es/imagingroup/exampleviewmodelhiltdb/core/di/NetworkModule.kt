package es.imagingroup.exampleviewmodelhiltdb.core.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.imagingroup.exampleviewmodelhiltdb.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val BASEURL = "http://10.0.2.2:8080/"


    @Provides
    fun providesHostSoccermngapi(builder: Retrofit.Builder): Retrofit =
        builder.baseUrl(BASEURL).build()

    @Provides
    fun provideBuilderRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient)
    }
    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(logging: HttpLoggingInterceptor, client: OkHttpClient.Builder): OkHttpClient {
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return client
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideOkhttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }


    @Provides
    @Singleton
    fun provideHttpLoginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

}