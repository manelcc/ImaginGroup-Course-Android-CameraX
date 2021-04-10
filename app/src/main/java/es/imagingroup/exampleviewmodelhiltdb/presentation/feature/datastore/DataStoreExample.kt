package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.datastore

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import es.imagingroup.exampleviewmodelhiltdb.UserProto
import es.imagingroup.exampleviewmodelhiltdb.databinding.DataStoreExampleBinding
import es.imagingroup.exampleviewmodelhiltdb.databinding.FragmentLeftBinding
import es.imagingroup.exampleviewmodelhiltdb.domain.model.settingsDataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DataStoreExample : Fragment() {

    private lateinit var binding: DataStoreExampleBinding


    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    val keyString = stringPreferencesKey("keyString")
    val keyInt = intPreferencesKey("keyInt")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataStoreExampleBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datatSorePreference()
        dataStoreProto()
    }

    private fun datatSorePreference() {
        //write
        GlobalScope.launch { writePreference() }

        //read
        lifecycleScope.launchWhenCreated {
            readPreference()?.collect {
                Log.i("datastore", "lectura del valor $it")
            }
        }
    }

    private suspend fun writePreference() {
        context?.dataStore?.edit {
            it[keyString] = "manel"
        }
    }

    private fun readPreference(): Flow<String>? {
        return context?.dataStore?.data?.map {
            it[keyString] ?: "no hay nada"
        }
    }

    private fun dataStoreProto() {
        lifecycleScope.launchWhenCreated {
            context?.settingsDataStore?.updateData { userProto ->
                userProto.toBuilder().setName("jose").setLastname("vela").setAge(55).build()
            }
        }

        lifecycleScope.launch {
            Log.e("datastore","value ${context?.settingsDataStore?.data?.first()}")
            context?.settingsDataStore?.data?.first()
            // You should also handle IOExceptions here.
        }



    }
}