package es.imagingroup.exampleviewmodelhiltdb.domain.usecase.impl


import es.imagingroup.domain.repository.LoginRepository
import es.imagingroup.exampleviewmodelhiltdb.domain.model.User
import es.imagingroup.exampleviewmodelhiltdb.domain.usecase.GetLoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : GetLoginUseCase {

    override fun logIn(userName: String, password: String): Flow<User> {
        return loginRepository.logIn(userName, password).flowOn(Dispatchers.IO)
    }


}