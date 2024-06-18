package com.phycare.residentbeacon.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResidentCompleteSearchItem(
    @SerializedName("ID")
    val ID: Int,

    @SerializedName("ProgramID")
    val ProgramID: Any,

    @SerializedName("Location")
    val Location: String,

    @SerializedName("Provider_Name")
    val Provider_Name: String,

    @SerializedName("PGY")
    val PGY: String,

    @SerializedName("ClassOf")
    val ClassOf: String,

    @SerializedName("UnderGraduateCollege")
    val UnderGraduateCollege: String,

    @SerializedName("MedicalSchool")
    val MedicalSchool: String,

    @SerializedName("Internship")
    val Internship: Any,

    @SerializedName("Major")
    val Major: Any,

    @SerializedName("Fellowship")
    val Fellowship: String,

    @SerializedName("HomeTown")
    val HomeTown: String,

    @SerializedName("MailID")
    val MailID: String?,

    @SerializedName("PhoneNo")
    val PhoneNo: String?,

    @SerializedName("Misc")
    val Misc: String,

    @SerializedName("Photo")
    val Photo: String,

    @SerializedName("FileName")
    val FileName: String,

    @SerializedName("TimeStamp")
    val TimeStamp: String,

    @SerializedName("ProgramName")
    val ProgramName: String,

    @SerializedName("Speciality")
    val Speciality: String,

    @SerializedName("ProgramLocation")
    val ProgramLocation: String


): Serializable