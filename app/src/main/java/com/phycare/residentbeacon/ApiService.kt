package com.phycare.residentbeacon

import com.phycare.residentbeacon.model.CompleteProgramSearchItem
import com.phycare.residentbeacon.model.PGYItem
import com.phycare.residentbeacon.model.ResidentCompleteSearchItem
import com.phycare.residentbeacon.model.SpecialityItem
import com.phycare.residentbeacon.model.StatesItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("ResidentServices.asmx/StatesMasterData")
    suspend fun getStates(): List<StatesItem>

    @POST("ResidentServices.asmx/PGYMasterData")
    suspend fun getPGYDetails(): List<PGYItem>

    @POST("ProgramDetailsServices.asmx/ProgramSpecialityMasterData")
    suspend fun getSpecialityDetails(): List<SpecialityItem>

    @GET("ResidentServices.asmx/ResidentCompleteSearch?")
    suspend fun getResidentCompleteSearch(
        @Query("Resident") resident: String?,
        @Query("Locations") location: String?,
        @Query("PGY") PGY: String?,
    ): List<ResidentCompleteSearchItem>

    // get program search
    @GET("ProgramDetailsServices.asmx/ProgramDetailsSearch?")
    suspend fun getProgramCompleteSearch(
        @Query("ProgramName") ProgramName: String?,
        @Query("Locations") Locations: String?,
        @Query("Specialities") Specialities: String?,
    ): List<CompleteProgramSearchItem>

   // get manage providers


}