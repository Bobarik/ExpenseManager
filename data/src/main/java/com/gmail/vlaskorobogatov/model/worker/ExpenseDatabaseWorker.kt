package com.gmail.vlaskorobogatov.model.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gmail.vlaskorobogatov.model.ExpenseDatabase
import com.gmail.vlaskorobogatov.model.entity.AccountEntity
import com.gmail.vlaskorobogatov.model.entity.OperationEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExpenseDatabaseWorker (context: Context, workerParameters: WorkerParameters): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val operationType = object : TypeToken<List<AccountEntity>>() {}.type
                        val operationEntityList: List<OperationEntity> = Gson().fromJson(jsonReader, operationType)

                        val database = ExpenseDatabase.getInstance(applicationContext)
                        database.operationDao().insert(operationEntityList)

                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "ExpenseDatabaseWorker"
        const val KEY_FILENAME = "EXPENSE_DATA_FILENAME"
    }
}