package com.phycare.residentbeacon

sealed class Screens (val route : String) {
   // object BottomHome : Screens("Home_screen")
    object Residents : Screens("Residents_screen")
    object Programs : Screens("Programs_screen")
    object ManageProviders : Screens("ManageProviders_screen")
    object Communications : Screens("Communications_screen")
}