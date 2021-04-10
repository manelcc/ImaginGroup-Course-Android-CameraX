package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.workmanager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import es.imagingroup.exampleviewmodelhiltdb.UserProtoEncript
import kotlinx.coroutines.delay

const val NAMEDATA = "nameData"
const val LASTNAMEDATA = "lastNameData"
const val AGEDATA = "ageData"

@HiltWorker
class ChangeUserWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val userProtoEncript: DataStore<UserProtoEncript>
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return runCatching {
            val name = inputData.getString("nameData")?:"Maria"
            val lastName = inputData.getString("lastNameData")?:"Fernandez"
            val age = inputData.getInt("ageData",25)

            delay(6000)
            userProtoEncript.updateData {
                it.toBuilder().setName(name).setLastname(lastName).setAge(age).build()
            }.also {
                WorkUtils().makeStatusNotification("Hemos cambiado el nombre a ${it.name}",applicationContext)
            }
            Result.success()
        }.getOrElse {
            Result.failure() }
    }
}