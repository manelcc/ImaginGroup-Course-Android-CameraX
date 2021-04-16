package es.imagingroup.exampleviewmodelhiltdb.data.api

import es.imagingroup.exampleviewmodelhiltdb.data.entities.ImageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiImages {

    /**
     * GET IMAGES
     */
    @GET("/api/getImages")
    suspend fun getListImages(): List<ImageResponse>

    /**
     * SAVE PHOTO
     */
    @Multipart
    @POST("/api/saveImages")
    suspend fun addImage(
        @PartMap partMap : @JvmSuppressWildcards Map<String, RequestBody>,
        @Part file : MultipartBody.Part
    ) : Response<Unit>

}