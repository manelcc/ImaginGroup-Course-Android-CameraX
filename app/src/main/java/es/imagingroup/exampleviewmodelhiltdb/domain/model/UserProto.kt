package es.imagingroup.exampleviewmodelhiltdb.domain.model

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.crypto.tink.Aead
import es.imagingroup.exampleviewmodelhiltdb.UserProtoEncript
import java.io.InputStream
import java.io.OutputStream

class UserProto (private val aead: Aead) : Serializer<UserProtoEncript> {
    override val defaultValue: UserProtoEncript = UserProtoEncript.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserProtoEncript {
        runCatching {
            val encryptedInput = input.readBytes()
            val decryptedInput = if (encryptedInput.isNotEmpty()) {
                aead.decrypt(encryptedInput, null)
            } else {
                encryptedInput
            }
            return UserProtoEncript.parseFrom(decryptedInput)
        }.getOrElse { throw CorruptionException("Error deserializing proto", it) }
    }

    override suspend fun writeTo(t: UserProtoEncript, output: OutputStream) {
        val encryptedBytes = aead.encrypt(t.toByteArray(), null)
        return runCatching { output.write(encryptedBytes) }.getOrNull()!!
    }

}




