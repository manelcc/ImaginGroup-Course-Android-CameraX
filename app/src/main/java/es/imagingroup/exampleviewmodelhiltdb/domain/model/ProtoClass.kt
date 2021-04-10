package es.imagingroup.exampleviewmodelhiltdb.domain.model

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import es.imagingroup.exampleviewmodelhiltdb.UserProto
import java.io.InputStream
import java.io.OutputStream


object ProtoClass : Serializer<UserProto> {
    override val defaultValue: UserProto = UserProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserProto {
        try {
            return UserProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: UserProto,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.settingsDataStore: DataStore<UserProto> by dataStore(
    fileName = "settings.pb",
    serializer = ProtoClass
)


