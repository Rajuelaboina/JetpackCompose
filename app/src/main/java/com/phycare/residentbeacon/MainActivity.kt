@file:OptIn(ExperimentalMaterial3Api::class)

package com.phycare.residentbeacon

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.phycare.residentbeacon.ui.theme.BeaconComposeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: ResidentViewModel by viewModels()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeaconComposeTheme {

               Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                  // viewModel.getAllStates()
                  //  BottomNavigationBar(viewModel, Modifier)
                  // BottomNavigationBar(viewModel,movieList = viewModel.movieListResponse)
                  //  PostList(movieList = viewModel.movieListResponse)
                  // MovieList(movieList = mainViewModel.movieListResponse)

               }
            }
        }
    }
}



@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DrawerAppComponentPreview() {
    val  viewModel = ResidentViewModel()
   // BottomNavigationBar(viewModel, Modifier, navController)
}
