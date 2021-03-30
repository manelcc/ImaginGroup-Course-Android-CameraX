package es.imagingroup.exampleviewmodelhiltdb.core.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.imagingroup.exampleviewmodelhiltdb.domain.exception.ErrorView

open class BaseViewModel: ViewModel() {

    protected val _loading:MutableLiveData<Boolean> = MutableLiveData()
    val loading:LiveData<Boolean> get() = _loading

    protected val _errorView:MutableLiveData<ErrorView> = MutableLiveData()
    val errorView:LiveData<ErrorView> get() = _errorView
}