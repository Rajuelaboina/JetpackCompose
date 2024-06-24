package com.phycare.residentbeacon

import androidx.navigation.NavHostController
import com.phycare.residentbeacon.AllDestinations.COMMUNICATION
import com.phycare.residentbeacon.AllDestinations.HOME
import com.phycare.residentbeacon.AllDestinations.LOGOUT
import com.phycare.residentbeacon.AllDestinations.MANAGEPROVIDERS
import com.phycare.residentbeacon.AllDestinations.PROGRAMS
import com.phycare.residentbeacon.AllDestinations.PROVIDERS

object AllDestinations {
    const val HOME = "Home"
    const val PROVIDERS = "Providers"
    const val PROGRAMS = "Programs"
    const val MANAGEPROVIDERS = "ManageProviders"
    const val COMMUNICATION = "Communication"
    const val LOGOUT = "Logout"

}
class AppNavigationActions(val navController: NavHostController) {

       fun navigateToHome(){
           navController.navigate(HOME){
               popUpTo(HOME)
           }
       }
    fun navigateToProviders(){
        navController.navigate(PROVIDERS){
            popUpTo(PROVIDERS)
        }
    }
    fun navigateToPrograms(){
        navController.navigate(PROGRAMS){
            popUpTo(PROGRAMS)
        }
    }
    fun navigateToManageProviders(){
        navController.navigate(MANAGEPROVIDERS){
            popUpTo(MANAGEPROVIDERS)
        }
    }
    fun navigateToCommunication(){
        navController.navigate(COMMUNICATION){
            popUpTo(COMMUNICATION)
        }
    }

      fun navigateToLogout(){
          navController.navigate(LOGOUT)
          {
              launchSingleTop = true
              restoreState = true
          }
      }
}