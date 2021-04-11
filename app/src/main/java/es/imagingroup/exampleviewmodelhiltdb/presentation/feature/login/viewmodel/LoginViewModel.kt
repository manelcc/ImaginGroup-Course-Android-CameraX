package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.imagingroup.exampleviewmodelhiltdb.core.view.BaseViewModel
import es.imagingroup.exampleviewmodelhiltdb.domain.exception.ErrorView
import es.imagingroup.exampleviewmodelhiltdb.domain.model.User
import es.imagingroup.exampleviewmodelhiltdb.domain.usecase.GetLoginUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: GetLoginUseCase
) : BaseViewModel() {

    val _userName: MutableLiveData<String> = MutableLiveData()
    val userName: LiveData<String> get() = _userName
    val _password: MutableLiveData<String> = MutableLiveData()
    val password: LiveData<String> get() = _password


    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> get() = _user


    fun enableBtn(userName: String?, password: String?): Boolean {
        return userName?.isNotBlank() == true && password?.isNotBlank() == true
    }

    fun logIn(userName: String, password: String) {
        viewModelScope.launch {
            loginUseCase.getImages()
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _errorView.value = it as ErrorView }
                .collect {
                    Log.i("manel","informacion $it") }
        }





       /* viewModelScope.launch {
            loginUseCase.logIn(userName, password)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _errorView.value = it as ErrorView }
                .collect { _user.value = it }
        }*/
    }

    fun clearValues() {
        _user.value = User()
    }

}