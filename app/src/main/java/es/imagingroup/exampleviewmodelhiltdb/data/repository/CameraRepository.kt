package es.imagingroup.exampleviewmodelhiltdb.data.repository

import es.imagingroup.exampleviewmodelhiltdb.data.entities.ImageResponse
import kotlinx.coroutines.flow.Flow

interface CameraRepository {

    fun loadImages(): Flow<ImageResponse>
    fun savePhoto(nameFile: String, pathFile: String): Flow<Boolean>
    fun saveVideo(nameFile: String, pathFile: String): Flow<Boolean>
}
