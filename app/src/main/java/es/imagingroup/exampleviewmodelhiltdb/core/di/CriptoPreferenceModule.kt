package es.imagingroup.exampleviewmodelhiltdb.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.crypto.tink.Aead
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.imagingroup.exampleviewmodelhiltdb.UserProtoEncript
import es.imagingroup.exampleviewmodelhiltdb.presentation.feature.datastore.DataStoreManager
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CriptoPreferenceModule {

    private const val KEYSET_NAME = "master_keyset"
    private const val PREFERENCE_FILE = "master_key_preference"
    private const val MASTER_KEY_URI = "android-keystore://master_key"

    @Provides
    fun provideMainKey(@ApplicationContext applicationContext: Context): MasterKey {
        return MasterKey.Builder(applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .setUserAuthenticationRequired(true)
            .build()
    }

    @Provides
    fun providePreferences(@ApplicationContext applicationContext: Context, mainKey: MasterKey): SharedPreferences {
        return EncryptedSharedPreferences.create(
            applicationContext,
            "preference_MatchCenter",
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


    @Provides
    fun provideAead(@ApplicationContext applicationContext: Context): Aead {
        AeadConfig.register()
        return AndroidKeysetManager.Builder()
            .withSharedPref(applicationContext, KEYSET_NAME, PREFERENCE_FILE)
            .withKeyTemplate(AesGcmKeyManager.aes256GcmTemplate())
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle
            .getPrimitive(Aead::class.java)
    }


    @Singleton
    @Provides
    fun provideDataStoreEncript(@ApplicationContext context: Context, aead: Aead): DataStore<UserProtoEncript> = DataStoreManager(context, aead).dataStore


}


