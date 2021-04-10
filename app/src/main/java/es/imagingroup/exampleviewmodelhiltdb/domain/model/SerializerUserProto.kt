package es.imagingroup.exampleviewmodelhiltdb.domain.model

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.crypto.tink.Aead
import dagger.hilt.android.qualifiers.ApplicationContext
import es.imagingroup.exampleviewmodelhiltdb.UserProtoEncript

import javax.inject.Inject

class SerializerUserProto @Inject constructor(
    private  @ApplicationContext val context: Context,
    userProtoEncript: UserProto) {


   /* val  Context.dataStoreManager: DataStore<UserProtoEncript> by dataStore(
        fileName = "settings.pb",
        serializer = userProtoEncript
    )

    val Context.dataStoreManager: DataStore<UserProtoEncript> by dataStore(
        fileName = "user.pb",
        serializer = UserProto(aead)
    )*/

    fun getStore(): DataStore<UserProtoEncript>? {
        //return context.dataStoreManager
        return null
    }


}

