package com.phycare.residentbeacon

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.phycare.residentbeacon.ui.theme.BeaconComposeTheme

class HomeActivity : ComponentActivity() {
    private val viewModel: ResidentViewModel by viewModels()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            BeaconComposeTheme {
               /* Scaffold(modifier = Modifier.fillMaxSize()) {


                }*/
                  HomeAppNavGraph()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    BeaconComposeTheme {
        HomeAppNavGraph()
    }
}