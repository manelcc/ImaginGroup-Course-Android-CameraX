package es.imagingroup.exampleviewmodelhiltdb.data.exception

import es.imagingroup.exampleviewmodelhiltdb.domain.exception.ErrorView
import retrofit2.HttpException

fun getError(exception: Throwable): ErrorView {
    return try {
        when ((exception as HttpException).code()) {
            401 -> ErrorView.ExpiredSession()
            500 -> ErrorView.ServerException()
            else -> ErrorView.ServerException("service error")
        }
    } catch (exception: ClassCastException) {
        ErrorView.ServerException()
    }
}