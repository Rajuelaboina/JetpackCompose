package com.phycare.residentbeacon

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.phycare.residentbeacon.screens.Credentials

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyProfileData",Context.MODE_PRIVATE)

    fun saveData(credentials: Credentials){
        val editor = sharedPreferences.edit()
        editor.putString("UserName",credentials.login)
        editor.putString("Password",credentials.pwd)
        editor.putBoolean("CHECKED",credentials.remember)
        editor.apply()
        editor.commit()
    }
    fun getData(): Credentials {
        val userName = sharedPreferences.getString("UserName","")
        val password = sharedPreferences.getString("Password","")
        val remem = sharedPreferences.getBoolean("CHECKED",false)
        return Credentials(userName.toString(),password.toString(),remem)
    }
    fun rememberSaveData(credentials: Credentials){
        val editor = sharedPreferences.edit()
        editor.putString("REMUserName",credentials.login)
        editor.putString("REMPassword",credentials.pwd)
        editor.putBoolean("REMCHECKED",credentials.remember)
        editor.apply()
        editor.commit()
    }
    fun rememberGetData(): Credentials {
        val userName = sharedPreferences.getString("REMUserName",null)
        val password = sharedPreferences.getString("REMPassword",null)
        val remem = sharedPreferences.getBoolean("REMCHECKED",false)
        return Credentials(userName.toString(),password.toString(),remem)
    }
    fun clearData(context: Context) {
        val editor = sharedPreferences.edit()
        editor.remove("REMUserName")
        editor.remove("REMPassword")
        editor.remove("REMCHECKED")
        editor.apply()
        context.startActivity(Intent(context,LoginActivity::class.java))

    }
}