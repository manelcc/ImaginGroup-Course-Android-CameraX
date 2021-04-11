package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.camera.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.imagingroup.exampleviewmodelhiltdb.core.view.BaseViewModel
import es.imagingroup.exampleviewmodelhiltdb.domain.exception.ErrorView
import es.imagingroup.exampleviewmodelhiltdb.domain.usecase.GetImagesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraPreviewViewModel @Inject constructor(
    private val useCaseCamera: GetImagesUseCase
) : BaseViewModel() {

    private val _photoSave = MutableLiveData<Boolean>()
    val photoSave: LiveData<Boolean> = _photoSave

    fun saveImage(nameFile: String, absolutePath: String) {
        viewModelScope.launch {
            useCaseCamera.savePhoto(nameFile, absolutePath)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _errorView.value = it as ErrorView }
                .collect { _photoSave.value = it }
        }
    }


}