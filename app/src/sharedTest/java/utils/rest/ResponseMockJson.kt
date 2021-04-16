package utils.rest

import androidx.annotation.NonNull
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.imagingroup.exampleviewmodelhiltdb.data.entities.ImageResponse


import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.lang.reflect.Type

class ResponseMockJson {

    companion object{
        fun <T> manel(json: String): T {
            return Gson().fromJson(json, object : TypeToken<List<ImageResponse>>() {}.type)
        }
    }

    private infix fun String.concat(anotherString: String): String = this.plus(anotherString)
    private fun getInputStreamFromResource(fileName: String) = javaClass.classLoader?.getResourceAsStream(fileName)

    @Throws(FileNotFoundException::class)
    fun <T> responseMock(file: String, @NonNull body: Class<T>): T {
        val result = String(readBinariFileFromResources(file concat ".json"))
        return when {
            result.isEmpty() -> throw FileNotFoundException("exception open file")
            else -> Gson().fromJson(result, body)
        }
    }

    @Throws(FileNotFoundException::class)
    fun <T> responseMockList(file: String, @NonNull body: Class<T>): List<T> {
        val result = String(readBinariFileFromResources(file concat ".json"))
        return when {
            result.isEmpty() -> emptyList()
            result.isNotEmpty() -> return Gson().fromJson(result, TypeToken.getParameterized(MutableList::class.java, body).type)
            else -> throw FileNotFoundException("exception open file")
        }
    }

    private fun readBinariFileFromResources(fileName: String): ByteArray {
        ByteArrayOutputStream().use { byteStream ->
            getInputStreamFromResource(fileName)?.copyTo(byteStream)
            return byteStream.toByteArray()
        }
    }

/*

    internal inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
        */

}
