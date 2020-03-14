package com.sunanda.wtpharinghata.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class RowTable : Serializable {

    @PrimaryKey(autoGenerate = true)
    var rid: Int = 0

    @ColumnInfo(name = "rdate")
    var rdate: String? = null

    @ColumnInfo(name = "rtime")
    var rtime: String? = null

    @ColumnInfo(name = "wtp_name")
    var wtp_name: String? = null

    @ColumnInfo(name = "raw")
    var raw: String? = null

    @ColumnInfo(name = "treated")
    var treated: String? = null
}