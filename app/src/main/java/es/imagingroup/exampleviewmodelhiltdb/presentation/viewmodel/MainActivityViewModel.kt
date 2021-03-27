package es.imagingroup.exampleviewmodelhiltdb.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivityViewModel:ViewModel() {

    fun print(text: String) {
        Log.i("example",text)

        viewModelScope.launch {
            workerThread().collectLatest {
                Log.i("example","observe MainThread? ${Thread.currentThread()}")
            }
        }
    }


    fun workerThread(): Flow<String> {
       return flow {
           delay(1000)
           Log.i("example","observe workerThread? ${Thread.currentThread()}")
           emit("Emito en segundo hilo")
       }.flowOn(Dispatchers.IO)
    }
}