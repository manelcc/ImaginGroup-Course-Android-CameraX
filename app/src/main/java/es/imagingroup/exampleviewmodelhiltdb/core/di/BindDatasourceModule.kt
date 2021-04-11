package es.imagingroup.exampleviewmodelhiltdb.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.imagingroup.exampleviewmodelhiltdb.data.datasource.ImagesDatasource
import es.imagingroup.exampleviewmodelhiltdb.data.datasource.ImagesDatasourceImpl


@Module
@InstallIn(SingletonComponent::class)
abstract class BindDatasourceModule {

    @Binds
    abstract fun bindDatasourceImages(imagesDatasourceImpl: ImagesDatasourceImpl): ImagesDatasource

}