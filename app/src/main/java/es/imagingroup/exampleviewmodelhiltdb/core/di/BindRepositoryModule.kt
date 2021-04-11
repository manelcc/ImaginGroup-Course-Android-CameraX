package es.imagingroup.exampleviewmodelhiltdb.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import es.imagingroup.domain.repository.LoginRepository
import es.imagingroup.exampleviewmodelhiltdb.data.datasource.ImagesDatasource
import es.imagingroup.exampleviewmodelhiltdb.data.datasource.ImagesDatasourceImpl
import es.imagingroup.exampleviewmodelhiltdb.data.repository.CameraRepository
import es.imagingroup.exampleviewmodelhiltdb.data.repository.impl.CameraRepositoryImpl
import es.imagingroup.exampleviewmodelhiltdb.data.repository.impl.LoginRepositoryImpl
import es.imagingroup.exampleviewmodelhiltdb.domain.usecase.GetLoginUseCase
import es.imagingroup.exampleviewmodelhiltdb.domain.usecase.impl.GetLoginUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class BindRepositoryModule {
    @Binds
    abstract fun bindLoginRepository(repository: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindImagesRepository(repository: CameraRepositoryImpl): CameraRepository
}