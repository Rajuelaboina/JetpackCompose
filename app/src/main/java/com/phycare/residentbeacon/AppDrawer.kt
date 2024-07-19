package com.phycare.residentbeacon

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    route: String,
    modifier: Modifier = Modifier,
    navigateToHome: ()->Unit = {},
    navigateToProviders: ()-> Unit = {},
    navigateToPrograms: ()-> Unit = {},
    navigateToManageProviders: ()-> Unit = {},
    navigateToCommunication: ()-> Unit = {},
    navigateToLogout: ()-> Unit = {},
    closeDrawer: () -> Unit = {}
) {
    ModalDrawerSheet{
        DrawerHeader(modifier)
      // Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_padding)))
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.slide_menucolor))
        )
        {

        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(id = R.string.home),
                    style = MaterialTheme.typography.labelLarge,
                    )
            },
            selected = route == AllDestinations.HOME,
            onClick = {
                navigateToHome()
                closeDrawer()
            },
            icon = { Icon(painterResource(id = R.drawable.nav_home2),
                contentDescription = null, modifier = Modifier.size(25.dp)) },
            shape = MaterialTheme.shapes.medium,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color.DarkGray,
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = Color.White,
                //unselectedTextColor = Color.Black,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Black
            ),
            modifier = Modifier
                .padding(end = 10.dp, start = 10.dp)
                .clip(RoundedCornerShape(30.dp))

        )
        NavigationDrawerItem(
            label = { Text(text = stringResource(id = R.string.providers),
                style = MaterialTheme.typography.labelLarge) },
            selected = route == AllDestinations.PROVIDERS,
            onClick = {
                navigateToProviders()
                closeDrawer()
            },
            icon = { Icon(painterResource(id = R.drawable.nav_icon12), contentDescription = null) },
            shape = MaterialTheme.shapes.medium,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color.DarkGray,
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = Color.White,
               // unselectedTextColor = Color.Black,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Black
            ),
            modifier = Modifier
                .padding(end = 10.dp, start = 10.dp)
                .clip(RoundedCornerShape(30.dp))

        )
        NavigationDrawerItem(
            label = { Text(text = stringResource(id = R.string.programs),
                style = MaterialTheme.typography.labelLarge) },
            selected = route == AllDestinations.PROGRAMS,
            onClick = {
                navigateToPrograms()
                closeDrawer()
            },
            icon = { Icon(painterResource(id = R.drawable.nav_icon22), contentDescription = null) },
            shape = MaterialTheme.shapes.medium,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color.DarkGray,
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = Color.White,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Black
            ),
            modifier = Modifier
                .padding(end = 10.dp, start = 10.dp)
                .clip(RoundedCornerShape(30.dp))

        )
        NavigationDrawerItem(
            label = { Text(text = stringResource(id = R.string.manageProviders),
                style = MaterialTheme.typography.labelLarge) },
            selected = route == AllDestinations.MANAGEPROVIDERS,
            onClick = {
                navigateToManageProviders()
                closeDrawer()
            },
            icon = { Icon(painterResource(id = R.drawable.nav_icon32), contentDescription = null) },
            shape = MaterialTheme.shapes.medium,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color.DarkGray,
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = Color.White,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Black
            ),
            modifier = Modifier
                .padding(end = 10.dp, start = 10.dp)
                .clip(RoundedCornerShape(30.dp))

        )
        NavigationDrawerItem(
            label = { Text(text = stringResource(id = R.string.communication),
                style = MaterialTheme.typography.labelLarge) },
            selected = route == AllDestinations.COMMUNICATION,
            onClick = {
                navigateToCommunication()
                closeDrawer()
            },
            icon = { Icon(painterResource(id = R.drawable.nav_icon42) , contentDescription = null) },
            shape = MaterialTheme.shapes.medium,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color.DarkGray,
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = Color.White,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Black
            ),
            modifier = Modifier
                .padding(end = 10.dp, start = 10.dp)
                .clip(RoundedCornerShape(30.dp))

        )
        NavigationDrawerItem(
            label = { Text(text = stringResource(id = R.string.logout),
                style = MaterialTheme.typography.labelLarge) },
            selected = route == AllDestinations.LOGOUT,
            onClick = {
                navigateToLogout()
                closeDrawer()
            },
            icon = { Icon(painterResource(id = R.drawable.logout_24) , contentDescription = null) },
            shape = MaterialTheme.shapes.medium,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color.DarkGray,
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = Color.White,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Black
            ),
            modifier = Modifier
                .padding(end = 10.dp, start = 10.dp)
                .clip(RoundedCornerShape(30.dp))

        )

        }
    }
}
@Composable
fun DrawerHeader(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(color = colorResource(id = R.color.slide_menucolor))
            .padding(dimensionResource(id = R.dimen.header_padding))
            .fillMaxWidth()
    ) {

        Image(
            painterResource(id = R.drawable.doctor),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(dimensionResource(id = R.dimen.header_image_size))
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_padding)))

        Text(
            text = "Raju K S R",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color =  MaterialTheme.colorScheme.background,
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_padding)))
        Text(
            //text = stringResource(id = R.string.app_name),
            text =  "System Administrator",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.background,
        )
    }
}

@Preview
@Composable
fun DrawerHeaderPreview() {
    AppDrawer(modifier = Modifier, route = AllDestinations.HOME)
}
