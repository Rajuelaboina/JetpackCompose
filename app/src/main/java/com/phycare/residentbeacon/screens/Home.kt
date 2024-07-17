package com.phycare.residentbeacon.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.phycare.residentbeacon.BottomNavigationBar
import com.phycare.residentbeacon.ResidentViewModel

@Composable
fun HomeScreen(navController: NavHostController, viewModel: ResidentViewModel) {

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        // viewModel.getAllStates()
      //  BottomNavigationBar(viewModel)
        // BottomNavigationBar(viewModel,movieList = viewModel.movieListResponse)
        //  PostList(movieList = viewModel.movieListResponse)
        // MovieList(movieList = mainViewModel.movieListResponse)

    }
}
