package es.imagingroup.exampleviewmodelhiltdb.data.repository.impl


import es.imagingroup.domain.repository.LoginRepository
import es.imagingroup.exampleviewmodelhiltdb.data.exception.getError
import es.imagingroup.exampleviewmodelhiltdb.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(
   // private val serializerUserProto: SerializerUserProto
) : LoginRepository {

    override fun logIn(userName: String, password: String): Flow<User> {
        return flow { emit(fakeLogin()) }.catch { throw getError(it) }
    }


    private suspend fun fakeLogin(): User {
        delay(3000)
        return User("manel", "cabezas", 49)

        /*.also {
            serializerUserProto?.getStore()?.updateData { userProtoEncript ->
                userProtoEncript.toBuilder().setName(it.name).setLastname(it.lastName).setAge(it.age).build()
            }
        }*/
    }

    private fun fakeExceptionLogin(): User {
        throw NullPointerException()

    }
}