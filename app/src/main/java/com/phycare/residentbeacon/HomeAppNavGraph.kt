package com.phycare.residentbeacon

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.phycare.residentbeacon.screens.CommunicationScreen2
import com.phycare.residentbeacon.screens.ManageProvidersScreen
import com.phycare.residentbeacon.screens.ProgramsScreen
import com.phycare.residentbeacon.screens.ProvidersScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val viewModel = ResidentViewModel()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: AllDestinations.HOME
    val navigationActions = remember(navController) { AppNavigationActions(navController) }


    ModalNavigationDrawer(drawerContent =
    {
        AppDrawer(
            route = currentRoute,
            navigateToHome = { navigationActions.navigateToHome() },
            navigateToProviders = { navigationActions.navigateToProviders() },
            navigateToPrograms = { navigationActions.navigateToPrograms() },
            navigateToManageProviders = { navigationActions.navigateToManageProviders() },
            navigateToCommunication = { navigationActions.navigateToCommunication() },
            navigateToLogout = { navigationActions.navigateToLogout() },
            closeDrawer = { coroutineScope.launch { drawerState.close() } }
        )
    }, drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                val context = LocalContext.current
                TopAppBar(title = { Text(text = currentRoute) },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }, content = {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                        })
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    actions = {
                        IconButton(onClick =
                        {
                            PreferencesManager(context).clearData(context)
                            // Toast.makeText(context, "Favorite", Toast.LENGTH_SHORT).show()
                        }
                        )
                        {
                            Icon(painterResource(id = R.drawable.logout_24), "")
                        }
                    }
                )
            }, modifier = Modifier
        ) {
            // ---- check the Inter network connection -- //

            // get the values from viewmodel
            viewModel.getAllStates()
            viewModel.getAllPGy()
            viewModel.getAllSpeciality()
            val stateList = viewModel.stateListResponse
            val pgyList = viewModel.pgyListResponse
            val specialityList = viewModel.specialityListResponse
            NavHost(
                navController = navController,
                startDestination = AllDestinations.PROVIDERS,
                modifier = modifier.padding(it)
            ) {
                composable(AllDestinations.HOME) {
                    HomeScreen()
                }
                composable(AllDestinations.PROVIDERS) {
                    viewModel.getAllStates()
                    viewModel.getAllPGy()
                    viewModel.getAllSpeciality()
                    val stateList = viewModel.stateListResponse
                    val pgyList = viewModel.pgyListResponse
                    val specialityList = viewModel.specialityListResponse
                    ProvidersScreen(
                        /*navController,*/viewModel,
                        stateList,
                        pgyList,
                        specialityList

                    )
                }
                composable(AllDestinations.PROGRAMS) {
                    viewModel.getAllStates()
                    viewModel.getAllPGy()
                    viewModel.getAllSpeciality()
                    val stateList = viewModel.stateListResponse
                    val pgyList = viewModel.pgyListResponse
                    val specialityList = viewModel.specialityListResponse
                    ProgramsScreen(
                        navController, viewModel, stateList, pgyList, specialityList
                    )
                }
                composable(AllDestinations.MANAGEPROVIDERS) {
                    ManageProvidersScreen(
                        navController, viewModel, stateList, pgyList, specialityList
                    )
                }
                composable(AllDestinations.COMMUNICATION) {
                    CommunicationScreen2(
                        navController, viewModel, stateList, pgyList, specialityList
                    )
                }
                composable(AllDestinations.LOGOUT) {
                    SettingsScreen(navController)
                }
            }
        }
    }
}
@Composable
fun SettingsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val alert = remember { mutableStateOf(true) }
    if (alert.value) {
        AlertDialog(onDismissRequest = { alert.value = false },
            title = { Text(text = "Exit App ") },
            //  text = { Text(text = "Do you want tApp Logout")},
            confirmButton = {
                Button(onClick = {
                    PreferencesManager(context).clearData(context)
                })
                {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        alert.value = false
                        navController.navigate(AllDestinations.PROVIDERS)
                    }) {
                    Text(text = "Cancel")
                }
           }
        )
    }
}
@Composable
fun HomeScreen() {

}
