package es.imagingroup.domain.repository

import es.imagingroup.exampleviewmodelhiltdb.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun logIn(userName: String, password: String): Flow<User>
}