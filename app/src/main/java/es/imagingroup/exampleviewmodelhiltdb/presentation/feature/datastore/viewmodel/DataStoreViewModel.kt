package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.datastore.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.imagingroup.exampleviewmodelhiltdb.UserProtoEncript
import es.imagingroup.exampleviewmodelhiltdb.core.view.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val userProtoEncript: DataStore<UserProtoEncript>
):BaseViewModel(){
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