package com.phycare.residentbeacon.model

import androidx.compose.runtime.Composable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_table")
data class Credentials(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val  id:Int = 0,
    @ColumnInfo(name="UserName")
    var login: String = "",
    @ColumnInfo(name="password")
    var pwd: String = "",
    var remember: Boolean = false,
    var isShowing: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return login.isNotEmpty() && pwd.isNotEmpty()
    }
}

