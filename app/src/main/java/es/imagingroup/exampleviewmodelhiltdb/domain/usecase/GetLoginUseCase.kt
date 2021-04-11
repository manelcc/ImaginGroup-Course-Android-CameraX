package es.imagingroup.exampleviewmodelhiltdb.domain.usecase

import es.imagingroup.exampleviewmodelhiltdb.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GetLoginUseCase {
    fun logIn(userName:String, password:String): Flow<User>

}