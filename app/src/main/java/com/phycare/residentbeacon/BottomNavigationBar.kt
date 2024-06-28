package com.phycare.residentbeacon

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.phycare.residentbeacon.screens.CommunicationScreen
import com.phycare.residentbeacon.screens.ManageProvidersScreen
import com.phycare.residentbeacon.screens.ProgramsScreen
import com.phycare.residentbeacon.screens.ProvidersScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigationBar(viewModel: ResidentViewModel) {
     val navController = rememberNavController()
     val navBackStackEntry by navController.currentBackStackEntryAsState()
     val currentDestination = navBackStackEntry?.destination
     Scaffold(modifier = Modifier.fillMaxSize(),
              topBar = {},
              bottomBar = {
              NavigationBar {
                   BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
                       NavigationBarItem(
                           selected = navigationItem.route == currentDestination?.route,
                           label = { Text(navigationItem.label, fontSize = 9.sp)},
                           icon = {

                               Icon(painter =painterResource(id =  navigationItem.icon), contentDescription = navigationItem.label )
                           },
                           onClick = {
                               navController.navigate(navigationItem.route) {
                                   popUpTo(navController.graph.findStartDestination().id) {
                                       saveState = true
                                   }
                                   launchSingleTop = true
                                   restoreState = true
                               }
                           },

                       )


                   }
              }
              }
     )
     { paddingValues ->

         viewModel.getAllStates()
         viewModel.getAllPGy()
         viewModel.getAllSpeciality()
         val  stateList = viewModel.stateListResponse
         val pgyList = viewModel.pgyListResponse
         val specialityList = viewModel.specialityListResponse
         NavHost(
             navController = navController,
             startDestination = Screens.Residents.route,
             modifier = Modifier.padding(paddingValues = paddingValues)) {
             composable(Screens.Residents.route) {

                 ProvidersScreen(
                     viewModel,stateList,pgyList,specialityList
                 )
             }
             composable(Screens.Programs.route) {
                 ProgramsScreen(
                     navController,viewModel,stateList,pgyList,specialityList
                 )
             }
             composable(Screens.ManageProviders.route) {
                 ManageProvidersScreen(
                     navController,viewModel,stateList,pgyList,specialityList
                 )
             }
             composable(Screens.Communications.route) {
                 CommunicationScreen(
                     navController,viewModel,stateList,pgyList,specialityList
                 )
             }

         }
     }


}