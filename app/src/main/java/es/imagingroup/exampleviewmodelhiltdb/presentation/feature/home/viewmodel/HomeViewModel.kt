package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import es.imagingroup.exampleviewmodelhiltdb.core.view.BaseViewModel
import es.imagingroup.exampleviewmodelhiltdb.domain.model.User
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): BaseViewModel() {


    val  _inputText:MutableLiveData<String> = MutableLiveData()

    val inputText:LiveData<String> get() = _inputText

    private val _userHome:MutableLiveData<User> = MutableLiveData()
    val userHome:LiveData<User> get() = _userHome

    fun setUserHome(user: User?) {
        Log.i("manel","value user VIEWMODEL ${user?.name}")
       _userHome.value = user
    }



}