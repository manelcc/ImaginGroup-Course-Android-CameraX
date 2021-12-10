package es.imagingroup.exampleviewmodelhiltdb.domain.usecase.impl

import es.imagingroup.exampleviewmodelhiltdb.data.entities.ImageResponse
import es.imagingroup.exampleviewmodelhiltdb.data.repository.CameraRepository
import es.imagingroup.exampleviewmodelhiltdb.domain.usecase.GetImagesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class GetImagesUseCaseImpl @Inject constructor(
    private val repository: CameraRepository
) : GetImagesUseCase {

    override fun getImages(): Flow<List<ImageResponse>> {

        return repository.loadImages()

    }

    override fun savePhoto(nameFile: String, absolutePath: String): Flow<Boolean> {
        return repository.savePhoto(nameFile, absolutePath)
    }
}