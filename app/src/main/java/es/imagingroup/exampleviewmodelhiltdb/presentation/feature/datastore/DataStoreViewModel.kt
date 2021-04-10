package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.datastore

import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import es.imagingroup.exampleviewmodelhiltdb.UserProtoEncript
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val userProtoEncript: DataStore<UserProtoEncript>
):ViewModel(){
    private val _userProtoEncript = MutableLiveData<UserProtoEncript>()
    val user:LiveData<UserProtoEncript> get() = _userProtoEncript

    init {
        getUserEncript()
    }

    fun getUserEncript(){
        viewModelScope.launch {
            userProtoEncript.data.collect { _userProtoEncript.value = it }
        }
    }

}