package com.phycare.residentbeacon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.phycare.residentbeacon.model.Credentials

@Dao
interface LoginDao {
   // @Query("SELECT * FROM login_table")
    @Insert
    fun insert(credentials: Credentials)
}