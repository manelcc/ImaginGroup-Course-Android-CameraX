package es.imagingroup.exampleviewmodelhiltdb.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.imagingroup.exampleviewmodelhiltdb.data.api.ApiImages
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provide(retrofit: Retrofit): ApiImages {
        return retrofit.create(ApiImages::class.java)
    }
}