package com.phycare.residentbeacon.model

import com.google.gson.annotations.SerializedName

data class CompleteProgramSearchItem(

    @SerializedName("ProgramID")
     var programID: Long,
    @SerializedName("ProgramName")
     val programName: String,

    @SerializedName("Speciality")
     val speciality: String,

    @SerializedName("Location")
     val location: String,

    @SerializedName("AdminInfo")
     val adminInfo: String,

    @SerializedName("ContactInfo")
     val contactInfo: String,

    @SerializedName("ProgramLink")
     val programLink: String,

    @SerializedName("ResidencyLink")
     val residencyLink: String,

    @SerializedName("LastUpdated")
     val lastUpdated: String,

    @SerializedName("SurveyReceived")
     val surveyReceived: String,

    @SerializedName("LocationInfo")
     val locationInfo: String,

    @SerializedName("Sponsor")
     val sponsor: String,

    @SerializedName("Participant1")
     val participant1: String,

    @SerializedName("Participant2")
     val participant2: String,

    @SerializedName("Participant3")
     val participant3: String,

    @SerializedName("ProgramType")
     val programType: String,

    @SerializedName("ProgramAffiliation")
     val programAffiliation: String,

    @SerializedName("AccreditedYears")
     val accreditedYears: Int,

    @SerializedName("RequiredYears")
     val requiredYears: Int,

    @SerializedName("AcceptingApplications")
     val acceptingApplications: String,

    @SerializedName("AcceptingNextYear")
     val acceptingNextYear: String,

    @SerializedName("StartingDate")
     val startingDate: String,

    @SerializedName("ERASParticipant")
     val eRASParticipant: String,

    @SerializedName("GovernmentAffiliated")
     val governmentAffiliated: String,

    @SerializedName("AdditionalComments")
     val additionalComments: String,
)