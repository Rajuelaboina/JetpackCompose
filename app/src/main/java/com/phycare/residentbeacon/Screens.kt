package com.phycare.residentbeacon

sealed class Screens (val route : String) {
   // object BottomHome : Screens("Home")
    object Residents : Screens("Residents")
    object Programs : Screens("Programs")
    object ManageProviders : Screens("ManageProviders")
    object Communications : Screens("Communications")
    object More : Screens("More")
}