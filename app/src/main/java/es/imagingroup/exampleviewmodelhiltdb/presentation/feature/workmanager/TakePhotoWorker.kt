package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.workmanager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.*
import androidx.work.*
import com.google.common.util.concurrent.ListenableFuture
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import es.imagingroup.exampleviewmodelhiltdb.data.datasource.ImagesDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@SuppressLint("RestrictedApi")
@HiltWorker
class TakePhotoWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val datasource: ImagesDatasource
) : CoroutineWorker(context, workerParameters) {


    companion object {
        private lateinit var lifeCycleOwner: LifecycleOwner

        fun initCamera(context: Context): LiveData<WorkInfo> {
            this.lifeCycleOwner = context as LifecycleOwner
            val work = PeriodicWorkRequestBuilder<TakePhotoWorker>(1,TimeUnit.MINUTES).build()
            WorkManager.getInstance(context).enqueue(work)
            return WorkManager.getInstance(context).getWorkInfoByIdLiveData(work.id)
        }
    }

    // Initialize our background executor
    private val cameraExecutor = Executors.newSingleThreadExecutor()


    private val photoFile by lazy {
        File(
            applicationContext.getExternalFilesDir(Environment.DIRECTORY_DCIM)?.absolutePath
                ?: applicationContext.externalCacheDir?.absolutePath,
            "${System.currentTimeMillis()}.jpg"
        )
    }


    private lateinit var photoCapture: ImageCapture

    override suspend fun doWork(): Result {


        startCamera()


        delay(10000)
        return Result.success()

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(applicationContext)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(applicationContext))
    }


    private fun bindPreview(cameraProvider: ProcessCameraProvider?) {
        cameraProvider?.unbindAll()
        Log.e("cameraProvider", "launchPhoto")

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val videoCapture = VideoCapture.Builder().apply {
            setTargetAspectRatio(AspectRatio.RATIO_16_9) // setting the aspect ration
            setCameraSelector(cameraSelector)
            setAudioBitRate(192000)
            setAudioSampleRate(44100)
            setAudioChannelCount(2)
            setVideoFrameRate(24) // setting the frame rate to 24 fps
            // setTargetRotation(binding.cameraPreview.display.rotation) // setting the rotation of the camera
        }.build()

        photoCapture = ImageCapture.Builder().apply {
            setIoExecutor(cameraExecutor)
            setTargetAspectRatio(AspectRatio.RATIO_16_9) // setting the aspect ration
            setCameraSelector(cameraSelector) // setting the lens facing (front or back)
            setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY) // setting to have pictures with highest quality possible
            //setTargetRotation(binding.cameraPreview.display.rotation) // setting the rotation of the camera
        }.build()





        cameraProvider?.bindToLifecycle(lifeCycleOwner, cameraSelector, photoCapture, videoCapture)

        capturePhoto()


    }

    fun capturePhoto() {
        with(photoCapture) {
            Log.e("cameraPreview", "take a photo")
            takePicture(androidx.camera.core.ImageCapture.OutputFileOptions.Builder(photoFile).build(), cameraExecutor, object :
                ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.e("cameraPreview", "Image File : ${photoFile.absolutePath}")
                    //viewModel.saveImage("photoClick_at_${System.currentTimeMillis()}", photoFile.absolutePath)
                    //saveImageWithWorkManager(photoFile)


                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("cameraPreview", "Video Error: ${exception.message}")

                }
            })
        }

    }


}