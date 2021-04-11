package es.imagingroup.exampleviewmodelhiltdb.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.imagingroup.exampleviewmodelhiltdb.domain.usecase.GetImagesUseCase
import es.imagingroup.exampleviewmodelhiltdb.domain.usecase.GetLoginUseCase
import es.imagingroup.exampleviewmodelhiltdb.domain.usecase.impl.GetImagesUseCaseImpl
import es.imagingroup.exampleviewmodelhiltdb.domain.usecase.impl.GetLoginUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class BindUseCaseModule {

    @Binds
    abstract fun bindUseCase(usecase: GetLoginUseCaseImpl): GetLoginUseCase

    @Binds
    abstract fun bindUseCaseImages(usecase: GetImagesUseCaseImpl): GetImagesUseCase
}