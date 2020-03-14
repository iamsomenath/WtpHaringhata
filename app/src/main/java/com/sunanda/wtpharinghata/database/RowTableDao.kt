package com.sunanda.wtpharinghata.database

import androidx.room.*


@Dao // Data Access Object
interface RowTableDao {

    @get:Query("SELECT * FROM rowtable")
    val getAllRowData: List<RowTable>

    @Query("SELECT * FROM rowtable WHERE rid = :rid")
    fun getRowData(rid: Int): RowTable

    @Query("SELECT rid FROM rowtable WHERE rdate = :rdate and rtime = :rtime and wtp_name = :wtp")
    fun isExists(rdate: String, rtime: String, wtp: String): Int

    @Query("UPDATE rowtable SET treated = :treated WHERE rid = :rid")
    fun updateRowTreated(treated: String, rid: Int): Int

    @Query("UPDATE rowtable SET raw = :raw WHERE rid = :rid")
    fun updateRowRaw(raw: String, rid: Int): Int

    @Insert
    fun insert(rowTable: RowTable)

    @Delete
    fun delete(rowTable: RowTable)

    @Update
    fun update(rowTable: RowTable)
}
