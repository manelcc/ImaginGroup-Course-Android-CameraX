package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.datastore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import es.imagingroup.exampleviewmodelhiltdb.UserProtoEncript
import es.imagingroup.exampleviewmodelhiltdb.core.extension.dataStore
import es.imagingroup.exampleviewmodelhiltdb.databinding.DataStoreExampleBinding
import es.imagingroup.exampleviewmodelhiltdb.domain.model.settingsDataStore
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class DataStoreExample : Fragment() {

    private var job: Job? = null
    private val viewModel: DataStoreViewModel by viewModels()
    private lateinit var binding: DataStoreExampleBinding

    private val settingsDataStore by lazy {
        context?.settingsDataStore
    }
    private val dataStorePreferences by lazy {
        context?.dataStore
    }
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
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datatSorePreference()
        dataStoreProtoValidPath()
        viewModel.user.observe(viewLifecycleOwner, this::readDataStoreEncriptDate)
    }

    private fun datatSorePreference() {
        //write and read
        lifecycleScope.launchWhenResumed {
            writePreference()
            delay(100)
            readPreference()?.collect {
                Log.i("datastore", "read value $it")
            }
        }
    }

    private suspend fun writePreference() {
        dataStorePreferences?.edit {
            it[keyString] = "manel"
        }
    }

    private fun readPreference(): Flow<String>? {
        return dataStorePreferences?.data?.map {
            it[keyString] ?: "no hay nada"
        }
    }

    private fun dataStoreProtoValidPath() {
        lifecycleScope.launchWhenResumed {
            settingsDataStore?.updateData { userProto ->
                userProto.toBuilder().setName("jose").setLastname("vela").setAge(55).build()
            }
            delay(3000)
            Log.e("datastore", "value ${context?.settingsDataStore?.data?.first()}")
            settingsDataStore?.data?.first()
        }
    }

    private fun readDataStoreEncriptDate(userProtoEncript: UserProtoEncript) {
        Snackbar.make(binding.root, "el valor guardado en Login es ${userProtoEncript.name}", Snackbar.LENGTH_SHORT).show()
    }


}