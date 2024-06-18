package com.phycare.residentbeacon


data class BottomNavigationItem(
    val label: String = "",
    val icon: Int = R.drawable.nav_icon1,
    val route: String = "",
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Providers",
                icon = R.drawable.nav_icon1,
                route = Screens.Residents.route
            ),
            BottomNavigationItem(
                label = "Programs",
                icon =  R.drawable.nav_icon2,
                route = Screens.Programs.route
            ),
            BottomNavigationItem(
                label = "ManageProviders",
                icon =  R.drawable.nav_icon4,
                route = Screens.ManageProviders.route
            ),
            BottomNavigationItem(
                label = "Communication",
                icon =  R.drawable.nav_icon3,
                route = Screens.Communications.route
            ),
        )
    }
}
