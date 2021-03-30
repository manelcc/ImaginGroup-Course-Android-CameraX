package es.imagingroup.exampleviewmodelhiltdb.domain.exception

sealed class ErrorView(message:String):Throwable(message){
    data class NetworkException(override val message: String = "no tengo red") : ErrorView(message)
    data class ParseException(val exception: Throwable,override val message: String = "no hemos podido procesar la información, prueba más tarde") : ErrorView(message)
    data class LoginException(override val message: String = "error en login") : ErrorView(message)
    data class ExpiredSession(override val message: String = "sesion expirada") : ErrorView(message)
    data class ServerException(override val message: String = "Ups! Disculpa, ahora no podemos facilitar los datos, prueba más tarde") : ErrorView(message)
}
