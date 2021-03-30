package es.imagingroup.exampleviewmodelhiltdb.data.repository.impl

import es.imagingroup.domain.repository.LoginRepository
import es.imagingroup.exampleviewmodelhiltdb.data.exception.getError
import es.imagingroup.exampleviewmodelhiltdb.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.lang.Exception
import java.lang.NullPointerException
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor():LoginRepository {

    override fun logIn(userName: String, password: String): Flow<User> {
        return flow { emit(fakeLogin()) }.catch { throw getError(it) }
    }

    private suspend fun fakeLogin():User{
        delay(3000)
        return User("manel","cabezas",49)
    }

    private fun fakeExceptionLogin():User{
        throw NullPointerException()
    }
}