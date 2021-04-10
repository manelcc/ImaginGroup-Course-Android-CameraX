package es.imagingroup.exampleviewmodelhiltdb.domain.model

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.crypto.tink.Aead
import dagger.hilt.android.qualifiers.ApplicationContext
import es.imagingroup.exampleviewmodelhiltdb.UserProtoEncript
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class UserProto @Inject constructor(
    private @ApplicationContext val context: Context,
    private val aead: Aead
) : Serializer<UserProtoEncript> {

    val Context.dataStoreManager: DataStore<UserProtoEncript> by dataStore(
        fileName = "user.pb",
        serializer = UserProto(context, aead)
    )

    fun getStore(): DataStore<UserProtoEncript> {
        return context.dataStoreManager
    }

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

