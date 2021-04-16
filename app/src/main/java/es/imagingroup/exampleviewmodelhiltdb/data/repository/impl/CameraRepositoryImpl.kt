package es.imagingroup.exampleviewmodelhiltdb.data.repository.impl

import es.imagingroup.exampleviewmodelhiltdb.data.datasource.ImagesDatasource
import es.imagingroup.exampleviewmodelhiltdb.data.entities.ImageResponse
import es.imagingroup.exampleviewmodelhiltdb.data.exception.getError
import es.imagingroup.exampleviewmodelhiltdb.data.repository.CameraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(
    private val imagesDatasource: ImagesDatasource
):CameraRepository {

    override fun loadImages(): Flow<List<ImageResponse>> {
        return imagesDatasource.loadImages().catch { throw getError(it) }
    }

    override fun savePhoto(nameFile:String, pathFile:String):Flow<Boolean>{
        return imagesDatasource.saveImage(nameFile, pathFile).catch { throw getError(it) }
    }

    override fun saveVideo(nameFile:String, pathFile:String):Flow<Boolean>{
        return flow { emit(true) }
    }
}