package com.sunanda.wtpharinghata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sunanda.wtpharinghata.database.Task
import com.sunanda.wtpharinghata.database.TaskDao

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}