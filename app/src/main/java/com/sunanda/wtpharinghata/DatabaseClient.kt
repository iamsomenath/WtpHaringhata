package com.sunanda.wtpharinghata

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room

class DatabaseClient private constructor(mCtx: Context) {

    //our app database object
    val appDatabase: AppDatabase = Room.databaseBuilder(mCtx, AppDatabase::class.java, "MyDB").build()

    init {
        //creating the app database with Room database builder
        //MyToDos is the name of the database
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var mInstance: DatabaseClient

        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient {
            mInstance = DatabaseClient(mCtx)
            return mInstance
        }
    }
}