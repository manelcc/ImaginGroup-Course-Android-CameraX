package es.imagingroup.exampleviewmodelhiltdb.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.imagingroup.domain.repository.LoginRepository
import es.imagingroup.exampleviewmodelhiltdb.data.repository.CameraRepository
import es.imagingroup.exampleviewmodelhiltdb.data.repository.impl.CameraRepositoryImpl
import es.imagingroup.exampleviewmodelhiltdb.data.repository.impl.LoginRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class BindRepositoryModule {
    @Binds
    abstract fun bindLoginRepository(repository: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindImagesRepository(repository: CameraRepositoryImpl): CameraRepository
}