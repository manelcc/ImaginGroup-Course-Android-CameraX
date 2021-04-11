package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.camera.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.livedatapermission.PermissionManager
import dagger.hilt.android.AndroidEntryPoint
import es.imagingroup.exampleviewmodelhiltdb.databinding.CameraPreviewBinding
import es.imagingroup.exampleviewmodelhiltdb.presentation.feature.camera.viewmodel.CameraPreviewViewModel
import es.imagingroup.exampleviewmodelhiltdb.presentation.feature.workmanager.NAMEFILE
import es.imagingroup.exampleviewmodelhiltdb.presentation.feature.workmanager.PATHPHOTO
import es.imagingroup.exampleviewmodelhiltdb.presentation.feature.workmanager.SavePhotoServerWorker
import kotlinx.coroutines.InternalCoroutinesApi
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@InternalCoroutinesApi
@SuppressLint("RestrictedApi")
@AndroidEntryPoint
class CameraPreview : Fragment(), PermissionManager.PermissionObserver {

    private lateinit var photoCapture: ImageCapture
    private lateinit var videoCapture: VideoCapture
    private lateinit var preview: Preview
    private lateinit var binding: CameraPreviewBinding
    private val viewModel: CameraPreviewViewModel by viewModels()

    // Initialize our background executor
    private val cameraExecutor = Executors.newSingleThreadExecutor()

    /**
     * A display listener for orientation changes that do not trigger a configuration
     * change, for example if we choose to override config change in manifest or for 180-degree
     * orientation changes.
     */
    private lateinit var displayManager: DisplayManager
    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit

        @SuppressLint("UnsafeExperimentalUsageError")
        override fun onDisplayChanged(displayId: Int) = view?.let { view ->
            if (displayId == -1) {
                preview.setTargetRotation(view.display.rotation)
                videoCapture.setTargetRotation(view.display.rotation)
            }
        } ?: Unit
    }

    private val videoFile by lazy {
        File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.absolutePath ?: requireContext().externalCacheDir?.absolutePath,
            "${System.currentTimeMillis()}.mp4"
        )
    }

    private val photoFile by lazy {
        File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_DCIM)?.absolutePath ?: requireContext().externalCacheDir?.absolutePath,
            "${System.currentTimeMillis()}.jpg"
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = CameraPreviewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //1 Iniciar los permisos requeridos
        PermissionManager.requestPermissions(
            this,
            3,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        displayManager = requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        displayManager.registerDisplayListener(displayListener, null)
        binding.cameraPreview.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View) =
                displayManager.registerDisplayListener(displayListener, null)

            override fun onViewAttachedToWindow(v: View) = displayManager.unregisterDisplayListener(displayListener)
        })

        observerViewModels()
    }

    private fun observerViewModels() {
        viewModel.photoSave.observe(viewLifecycleOwner, { result ->
            if (result) {
                Log.i("cameraPreview", "Hemos guardado la foto en el servidor")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        // Shut down our background executor
        cameraExecutor.shutdown()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider?) {
        cameraProvider?.unbindAll()
        preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .setTargetRotation(binding.cameraPreview.display.rotation)
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        videoCapture = VideoCapture.Builder().apply {
            setTargetAspectRatio(AspectRatio.RATIO_16_9) // setting the aspect ration
            setCameraSelector(cameraSelector)
            setAudioBitRate(192000)
            setAudioSampleRate(44100)
            setAudioChannelCount(2)
            setVideoFrameRate(24) // setting the frame rate to 24 fps
            setTargetRotation(binding.cameraPreview.display.rotation) // setting the rotation of the camera
        }.build()

        photoCapture = Builder().apply {
            setIoExecutor(cameraExecutor)
            setTargetAspectRatio(AspectRatio.RATIO_16_9) // setting the aspect ration
            setCameraSelector(cameraSelector) // setting the lens facing (front or back)
            setCaptureMode(CAPTURE_MODE_MINIMIZE_LATENCY) // setting to have pictures with highest quality possible
            setTargetRotation(binding.cameraPreview.display.rotation) // setting the rotation of the camera
        }.build()

        preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
        cameraProvider?.bindToLifecycle(this, cameraSelector, preview, photoCapture, videoCapture)
    }

    fun capturePhoto() {
        with(photoCapture) {
            takePicture(OutputFileOptions.Builder(photoFile).build(), cameraExecutor, object : OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: OutputFileResults) {
                    Log.i("cameraPreview", "Image File : ${photoFile.absolutePath}")
                    //viewModel.saveImage("photoClick_at_${System.currentTimeMillis()}", photoFile.absolutePath)
                    saveImageWithWorkManager(photoFile)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.i("cameraPreview", "Video Error: ${exception.message}")
                }
            })
        }
    }


    private fun saveImageWithWorkManager(photoCapture: File) {
        val myWorkRequest = OneTimeWorkRequestBuilder<SavePhotoServerWorker>()
            .setInputData(workDataOf(NAMEFILE to "WORKMANAGER photoClick_at_${System.currentTimeMillis()}",PATHPHOTO to photoCapture.absolutePath))
            .setInitialDelay(12,TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
    }

    fun captureVideo() {
        videoCapture.startRecording(
            VideoCapture.OutputFileOptions.Builder(videoFile).build(),
            cameraExecutor,
            object : VideoCapture.OnVideoSavedCallback {

                override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
                    Log.i("manel", "Video File : $videoFile")
                }

                override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
                    Log.i("manel", "Video Error: $message")
                }
            })
    }

    override fun setupObserver(permissionResultLiveData: LiveData<PermissionResult>) {
        //2 Si los permisos estan acceptados podemos iniciar el preview de la camara
        permissionResultLiveData.observe(this, {
            when (it) {
                is PermissionResult.PermissionGranted -> {
                    startCamera()
                }
                else -> Toast.makeText(requireContext(), "Necesitamos permisos para esta funcionalidad", Toast.LENGTH_SHORT).show()
            }
        })
    }
}