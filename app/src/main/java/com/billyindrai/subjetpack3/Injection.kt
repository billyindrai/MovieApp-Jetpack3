package com.billyindrai.subjetpack3

import android.content.Context
import com.billyindrai.subjetpack3.data.Repository
import com.billyindrai.subjetpack3.data.local.Database
import com.billyindrai.subjetpack3.data.local.LocalData
import com.billyindrai.subjetpack3.data.remote.api.RemoteDataSource

object Injection {
    fun repository(context: Context): Repository {
        val appExecutors = AppExecutors()
        val db = Database.getDatabase(context)
        val localData = LocalData.getInstance(db.databaseDAO())
        val remoteData = RemoteDataSource.getInstance()
        return Repository.getInstance(remoteData, localData, appExecutors)
    }
}