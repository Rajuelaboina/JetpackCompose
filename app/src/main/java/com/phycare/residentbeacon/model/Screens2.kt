package com.phycare.residentbeacon.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens2 (val route: String, val title: String) {

    sealed class HomeScreens(
        route: String,
        title: String,
        val icon: ImageVector
    ) : Screens2(
        route,
        title
    ) {
        object Favorite : HomeScreens("favorite", "Favorite", Icons.Filled.Favorite)
        object Notification : HomeScreens("notification", "Notification", Icons.Filled.Notifications)
        object MyNetwork : HomeScreens("network", "MyNetwork", Icons.Filled.Person)
    }

    sealed class DrawerScreens(
        route: String,
        title: String
    ) : Screens2(route, title) {
        object Home : DrawerScreens("home", "Home")
        object Account : DrawerScreens("account", "Account")
        object Help : DrawerScreens("help", "Help")
    }
}

val screensInHomeFromBottomNav = listOf(
    Screens2.HomeScreens.Favorite,
    Screens2.HomeScreens.Notification,
    Screens2.HomeScreens.MyNetwork
)

val screensFromDrawer = listOf(
    Screens2.DrawerScreens.Home,
    Screens2.DrawerScreens.Account,
    Screens2.DrawerScreens.Help,
)