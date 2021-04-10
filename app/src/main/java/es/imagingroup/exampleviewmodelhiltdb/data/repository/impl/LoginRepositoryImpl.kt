package es.imagingroup.exampleviewmodelhiltdb.data.repository.impl


import androidx.datastore.core.DataStore
import es.imagingroup.domain.repository.LoginRepository
import es.imagingroup.exampleviewmodelhiltdb.UserProtoEncript
import es.imagingroup.exampleviewmodelhiltdb.data.exception.getError
import es.imagingroup.exampleviewmodelhiltdb.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(
   private val userProtoEncript: DataStore<UserProtoEncript>
) : LoginRepository {

    override fun logIn(userName: String, password: String): Flow<User> {
        return flow { emit(fakeLogin(userProtoEncript)) }.catch { throw getError(it) }
    }


    private suspend fun fakeLogin(userProtoEncript: DataStore<UserProtoEncript>): User {
        delay(3000)
        return User("manel", "cabezas", 49).also {
            userProtoEncript.updateData { value ->
                value.toBuilder().setName(it.name).setLastname(it.lastName).setAge(it.age).build()
            }
        }
    }

    private fun fakeExceptionLogin(): User {
        throw NullPointerException()

    }
}