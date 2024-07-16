package com.phycare.residentbeacon

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.phycare.residentbeacon.model.Credentials

@Database(entities = [Credentials::class], version = 1)
abstract class LoginDataBase : RoomDatabase(){
    abstract fun loginDao(): LoginDao
   companion object{
       var databaseInstance: LoginDataBase? = null
       fun getInstance(context: Context):LoginDataBase{
           if(databaseInstance == null){
               databaseInstance = Room.databaseBuilder(context.applicationContext,
                   LoginDataBase::class.java,"login_dataBase")
                   //.fallbackToDestructiveMigration()
                   .allowMainThreadQueries()
                   .build()

           }
           return databaseInstance!!
       }

   }
}