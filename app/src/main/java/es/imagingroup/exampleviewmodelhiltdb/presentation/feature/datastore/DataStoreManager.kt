package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.google.crypto.tink.Aead
import dagger.hilt.android.qualifiers.ApplicationContext
import es.imagingroup.exampleviewmodelhiltdb.UserProtoEncript
import es.imagingroup.exampleviewmodelhiltdb.domain.model.UserProto
import javax.inject.Inject
import javax.inject.Singleton



class DataStoreManager @Inject constructor(
    private @ApplicationContext val context: Context,
    aead: Aead
) {
    private val Context.managerStore: DataStore<UserProtoEncript> by dataStore(
        fileName = "user.pb",
        serializer = UserProto(aead)
    )

    @Singleton
    val dataStore:DataStore<UserProtoEncript>
        get() = context.managerStore


}