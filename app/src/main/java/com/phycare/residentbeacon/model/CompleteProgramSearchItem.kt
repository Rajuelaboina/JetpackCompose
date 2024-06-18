package com.phycare.residentbeacon.model

data class CompleteProgramSearchItem(
    val AcceptingApplications: String,
    val AcceptingNextYear: String,
    val AccreditedYears: Int,
    val AdditionalComments: String,
    val AdminInfo: String,
    val ContactInfo: String,
    val ERASParticipant: String,
    val GovernmentAffiliated: String,
    val LastUpdated: String,
    val Location: String,
    val LocationInfo: String,
    val Participant1: String,
    val Participant2: String,
    val Participant3: String,
    val ProgramAffiliation: String,
    val ProgramID: Long,
    val ProgramLink: String,
    val ProgramName: String,
    val ProgramType: String,
    val RequiredYears: Int,
    val ResidencyLink: String,
    val Speciality: String,
    val Sponsor: String,
    val StartingDate: String,
    val SurveyReceived: String
)