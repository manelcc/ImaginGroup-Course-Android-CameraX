package es.imagingroup.exampleviewmodelhiltdb.data.datasource

import es.imagingroup.exampleviewmodelhiltdb.data.entities.ImageResponse
import kotlinx.coroutines.flow.Flow

interface ImagesDatasource {
    fun loadImages(): Flow<ImageResponse>
    fun saveImage(nameFile: String, pathFile: String): Flow<Boolean>
}