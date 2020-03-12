package com.sunanda.wtpharinghata.database

import androidx.room.*

@Dao // Data Access Object
interface TaskDao {

    @get:Query("SELECT * FROM task")
    val getAllData: List<Task>

    @Query("SELECT * FROM task WHERE id = :tid")
    fun getData(tid: Int): Task

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)
}
