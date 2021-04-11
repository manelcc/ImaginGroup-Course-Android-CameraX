package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import es.imagingroup.exampleviewmodelhiltdb.data.datasource.ImagesDatasource
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull

const val PATHPHOTO = "path"
const val NAMEFILE = "nameFile"


@InternalCoroutinesApi
@HiltWorker
class SavePhotoServerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val datasource: ImagesDatasource
): CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result {
            val nameFile = inputData.getString(NAMEFILE)?:""
            val pathFile = inputData.getString(PATHPHOTO)?:""

           return when(invoiceService(nameFile, pathFile)){
               true -> {
                   WorkUtils().makeStatusNotification("Hemos guardado la foto $nameFile",applicationContext)
                   Result.success()}
               else -> Result.failure()
           }
    }

    @InternalCoroutinesApi
    private suspend fun invoiceService(nameFile: String, pathFile: String): Boolean? {
        return datasource.saveImage(nameFile,pathFile).firstOrNull()
    }
}