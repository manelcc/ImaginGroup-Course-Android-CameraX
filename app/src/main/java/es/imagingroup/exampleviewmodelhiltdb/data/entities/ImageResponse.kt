package es.imagingroup.exampleviewmodelhiltdb.data.entities


import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("descripcionfile")
    val descripcionfile: String,
    @SerializedName("file")
    val imageFile: String ,//Del servicio viene un string es en el mapper que se convierte a ByteArray
    @SerializedName("id")
    val id: Int,
    @SerializedName("namefile")
    val namefile: String
)