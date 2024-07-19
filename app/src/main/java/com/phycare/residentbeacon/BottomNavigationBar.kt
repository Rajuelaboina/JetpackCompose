package com.phycare.residentbeacon

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    viewModel: ResidentViewModel,
    modifier: Modifier.Companion,
    navController: NavHostController
) {
    // val navController = rememberNavController()
     val navBackStackEntry by navController.currentBackStackEntryAsState()
     val currentDestination = navBackStackEntry?.destination
     var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    // Scaffold(modifier = Modifier.fillMaxSize(),
     //         topBar = {},
      //        bottomBar = {
              NavigationBar(modifier = modifier, containerColor  = Color.White) {
                   BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
                       NavigationBarItem(
                           selected = navigationItem.route == currentDestination?.route,
                           label = { Text(navigationItem.label, fontSize = 12.sp, maxLines = 1)},
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
                           colors = NavigationBarItemDefaults.colors(
                               selectedTextColor =colorResource(id = R.color.resident_color),
                               selectedIconColor = Color.White,
                               unselectedIconColor = Color.Black,
                               unselectedTextColor = Color.Black,
                               indicatorColor = colorResource(id = R.color.purple_700)
                           ),

                       )


                   }
              }
            //  }
    // )
     /*{ paddingValues ->

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
                     viewModel, stateList, pgyList, specialityList
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
             composable(Screens.More.route) {
                // showBottomSheet = true
                 More(
                     navController,viewModel,stateList,pgyList,specialityList
                 )
             }

         }
       *//*  if (showBottomSheet){
             ModalBottomSheet(onDismissRequest = { showBottomSheet = false }, sheetState = sheetState ) {
                //sheetContent
                 Column {
                     Text(text = "Sheet One")
                     Text(text = "Sheet One")
                     Text(text = "Sheet One")
                 }
             }
         }*//*
     }*/


}