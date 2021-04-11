package es.imagingroup.exampleviewmodelhiltdb.domain.usecase

import es.imagingroup.exampleviewmodelhiltdb.data.entities.ImageResponse
import kotlinx.coroutines.flow.Flow

interface GetImagesUseCase {
    fun getImages(): Flow<ImageResponse>
}