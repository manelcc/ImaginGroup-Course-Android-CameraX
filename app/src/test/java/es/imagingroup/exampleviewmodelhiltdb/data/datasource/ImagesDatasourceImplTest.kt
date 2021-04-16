package es.imagingroup.exampleviewmodelhiltdb.data.datasource

import app.cash.turbine.test
import es.imagingroup.exampleviewmodelhiltdb.data.api.ApiImages
import es.imagingroup.exampleviewmodelhiltdb.data.entities.ImageResponse
import es.imagingroup.exampleviewmodelhiltdb.domain.exception.ErrorView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`as whenever
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import utils.*
import kotlin.time.ExperimentalTime


@InternalCoroutinesApi
@ExperimentalTime
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ImagesDatasourceImplTest {

    @Mock
    lateinit var apiImages: ApiImages

    private lateinit var SUT: ImagesDatasourceImpl

    @Before
    fun setUp() {
        //init mocks si no incluimos el RunWith
        MockitoAnnotations.openMocks(this);
        SUT = ImagesDatasourceImpl(apiImages)
    }

    @DescriptionTest("en este caso validamos que obtenemos resultados con valores")
    @Test
    fun success_apiServices_Images() = runBlockingTest {
        //Given
        val responseImage:List<ImageResponse> = givenJsonMockList("getImagesResponse", ImageResponse::class.java)
        //When
        whenever(apiImages.getListImages()).thenReturn(responseImage)
        val actual = SUT.loadImages().single()
        //Then
        actual.size assertValue 12
        actual.size assertValue responseImage.size
        actual.map {
            it.descripcionfile.notNullValue()
        }
    }

    @DescriptionTest("en este caso validamos que obtenemos resultados con valores")
    @Test
    fun success_apiServices2_Images() = runBlockingTest {
        //Given
        val expectedResponse = givenJsonMockList("getImagesResponse", ImageResponse::class.java)
        //When
        whenever(apiImages.getListImages()).thenReturn(expectedResponse)
        val actual = SUT.loadImages()
        //Then
        actual.collect { it ->
            it.size assertValue 12
            it.size assertValue expectedResponse.size
            it.map {
                it.namefile.notNullValue() //=> El test falla que podemos hacer en esta situación?
                it.descripcionfile.notNullValue()//servicio falla mirar anotacion del modelo
            }
        }
    }

    @DescriptionTest("en este caso validamos que obtenemos resultados con valores")
    @Test
    fun success_apiServices3_Images() = runBlockingTest {
        //Given
        val expectedResponse = givenJsonMockList("getImagesResponse", ImageResponse::class.java)

        //When
        whenever(apiImages.getListImages()).thenReturn(expectedResponse)

        //Then
        SUT.loadImages().test {
            expectItem().size assertValue 12
            expectComplete()


        }

    }


    @DescriptionTest("en este caso validamos que emitimos el error correctamente y la assercion se realiza por anotación")
    @Test(expected = ErrorView.ServerException::class)
    fun error_apiServices_Images() = runBlockingTest {
        //When
        whenever(apiImages.getListImages()).thenThrow(NullPointerException::class.java)
        SUT.loadImages().single()

    }

    @DescriptionTest("en este caso validamos que emitimos el error correctamente y la assercion se realiza por anotación")
    @Test()
    fun error_apiServices2_Images() = runBlockingTest {
        //When
        whenever(apiImages.getListImages()).thenThrow(NullPointerException::class.java)
        SUT.loadImages().test {
            expectError()
        }

    }

    @DescriptionTest("en este caso validamos que emitimos el error correctamente y la assercion se realiza por anotación")
    @Test()
    fun error_apiServices_not_annotation_Images() = runBlockingTest {
        //When
        whenever(apiImages.getListImages()).thenThrow(NullPointerException::class.java)
        //Then
        SUT.loadImages().catch {
            this isInstanceOf ErrorView.ServerException::class.java
            (this as ErrorView.ServerException).message assertValue ErrorView.ServerException().message
        }
    }

    //Guardar la imagen

    @DescriptionTest("en este caso validamos que obtenemos resultados con valores")
    @Test
    fun success_apiServices_saveImage() = runBlockingTest {
        //Given
        val nameFile = mapOf("filename" to "nameFile".toRequestBody("text/plain".toMediaTypeOrNull()))
        val partFile = givenMock<MultipartBody.Part>()
        val response = Response.success(200,Unit)
        //When
        val value = apiImages.addImage(nameFile,partFile)


        val actual = SUT.saveImage("nameFile","pathFile").single()
        //Then
        actual equalsTo true
    }


}