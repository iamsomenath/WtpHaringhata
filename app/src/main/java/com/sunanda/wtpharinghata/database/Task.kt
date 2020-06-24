package com.sunanda.wtpharinghata.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.io.Serializable

@Entity
class Task : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "taskId")
    var taskId: String? = null

    @ColumnInfo(name = "alachlor")
    var alachlor: String? = null

    @ColumnInfo(name = "atrazine")
    var atrazine: String? = null

    @ColumnInfo(name = "aldrin")
    var aldrin: String? = null

    @ColumnInfo(name = "alpha_hch")
    var alpha_hch: String? = null

    @ColumnInfo(name = "beta_hch")
    var beta_hch: String? = null

    @ColumnInfo(name = "butachlor")
    var butachlor: String? = null

    @ColumnInfo(name = "chlorpyriphos")
    var chlorpyriphos: String? = null

    @ColumnInfo(name = "delta_hch")
    var delta_hch: String? = null

    @ColumnInfo(name = "dichlor")
    var dichlor: String? = null

    @ColumnInfo(name = "endosulfan")
    var endosulfan: String? = null

    @ColumnInfo(name = "ethion")
    var ethion: String? = null

    @ColumnInfo(name = "lindane")
    var gamma: String? = null

    @ColumnInfo(name = "isoproturon")
    var isoproturon: String? = null

    @ColumnInfo(name = "malathion")
    var malathion: String? = null

    @ColumnInfo(name = "methyl")
    var methyl: String? = null

    @ColumnInfo(name = "monocrotophos")
    var monocrotophos: String? = null

    @ColumnInfo(name = "phorate")
    var phorate: String? = null

    @ColumnInfo(name = "bromoform")
    var bromoform: String? = null

    @ColumnInfo(name = "dibromochloromethane")
    var dibromochloromethane: String? = null

    @ColumnInfo(name = "bromochloromethane")
    var bromochloromethane: String? = null

    @ColumnInfo(name = "chloroform")
    var chloroform: String? = null

    @ColumnInfo(name = "type")
    var type: String? = null

    @ColumnInfo(name = "wtpname")
    var wtpname: String? = null

    @ColumnInfo(name = "entrydate")
    var entrydate: String? = null

    // new filed added
    @ColumnInfo(name = "endosulfan1")
    var endosulfan1: String? = null

    @ColumnInfo(name = "endosulfan2")
    var endosulfan2: String? = null

    @ColumnInfo(name = "op_ddt")
    var op_ddt: String? = null

    @ColumnInfo(name = "pp_ddt")
    var pp_ddt: String? = null

    @ColumnInfo(name = "bromodichloromethane")
    var bromodichloromethane: String? = null

    @ColumnInfo(name = "chlorodibromomethane")
    var chlorodibromomethane: String? = null
}
