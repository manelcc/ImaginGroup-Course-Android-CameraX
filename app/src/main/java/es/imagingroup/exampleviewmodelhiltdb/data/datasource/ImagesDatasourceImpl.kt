package es.imagingroup.exampleviewmodelhiltdb.data.datasource

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.VisibleForTesting
import es.imagingroup.exampleviewmodelhiltdb.data.api.ApiImages
import es.imagingroup.exampleviewmodelhiltdb.data.entities.ImageResponse
import es.imagingroup.exampleviewmodelhiltdb.data.exception.getError
import es.imagingroup.exampleviewmodelhiltdb.domain.exception.ErrorView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject
import kotlin.math.roundToInt

class ImagesDatasourceImpl @Inject constructor(private val apiImages: ApiImages) : ImagesDatasource {

    override fun loadImages(): Flow<List<ImageResponse>> {
        return flow {
            emit(apiImages.getListImages())
        }.catch { throw getError(it) }
    }

    override fun saveImage(nameFile: String, pathFile: String): Flow<Boolean> {
        return flow {
            val result = apiImages.addImage(getPartMap(nameFile), getFile(pathFile))
            when (result.code()) {
                200 -> emit(true)
                else -> throw ErrorView.ServerException()
            }
        }.catch {
            throw getError(it)
        }
    }

    private fun getPartMap(nameFile: String): @JvmSuppressWildcards Map<String, RequestBody> {
        return mapOf("filename" to nameFile.toRequestBody("text/plain".toMediaTypeOrNull()))
    }

    @VisibleForTesting
    fun getFile(path: String): MultipartBody.Part {
        //val bitmap: Bitmap? = null
        val file = File(path)
        resizeBitmap(file, path)
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", "photo.jpg", requestBody)
    }

    private fun resizeBitmap(file: File, path: String) {
        val bitmap: Bitmap?
        val bytes = ByteArrayOutputStream()

        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(path)
            try {
                bitmap?.let {
                    val newWith: Int = (it.width * 0.50).roundToInt()
                    val newHeight: Int = (it.height * 0.50).roundToInt()
                    Bitmap.createScaledBitmap(it, newWith, newHeight, true)
                    it.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                    file.writeBytes(bytes.toByteArray())
                }
            } catch (exception: Exception) {
                Log.e(javaClass.simpleName, "exception write camera ${exception.message}")
            }

        }
    }
}