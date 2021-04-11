package es.imagingroup.exampleviewmodelhiltdb.data.entities


import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("descripcionfile")
    val descripcionfile: String,
    @SerializedName("file")
    val imageFile: ByteArray ,
    @SerializedName("id")
    val id: Int,
    @SerializedName("namefile")
    val namefile: String
)