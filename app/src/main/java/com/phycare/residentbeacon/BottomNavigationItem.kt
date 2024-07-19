package com.phycare.residentbeacon


data class BottomNavigationItem(
    val label: String = "",
    val icon: Int = R.drawable.nav_icon1,
    val route: String = "",
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
           /* BottomNavigationItem(
                label = "Home",
                icon = R.drawable.nav_home2,
                route = Screens.BottomHome.route
            ),*/
            BottomNavigationItem(
                label = "Providers",
                icon = R.drawable.nav_icon12,
                route = Screens.Residents.route
            ),
            BottomNavigationItem(
                label = "Programs",
                icon =  R.drawable.nav_icon22,
                route = Screens.Programs.route
            ),
            BottomNavigationItem(
                label = "ManageProviders",
                icon =  R.drawable.nav_icon32,
                route = Screens.ManageProviders.route
            ),
            BottomNavigationItem(
                label = "Communication",
                icon =  R.drawable.nav_icon42,
                route = Screens.Communications.route
            ),
            BottomNavigationItem(
                label = "More",
                icon =  R.drawable.baseline_more_horiz_24,
                route = Screens.More.route
            ),
        )
    }
}
