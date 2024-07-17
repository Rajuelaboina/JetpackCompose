package com.phycare.residentbeacon

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phycare.residentbeacon.ui.theme.BeaconComposeTheme

class HomeActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            var strTitle: String
            var networkStatus by remember { mutableStateOf(false) }
            var isNetwork by remember { mutableStateOf(false) }
            // val scope = rememberCoroutineScope()
            BeaconComposeTheme {
                /* Scaffold(modifier = Modifier.fillMaxSize()) {}*/
                val context = LocalContext.current
                DisposableEffect(context, "android.net.conn.CONNECTIVITY_CHANGE") {
                    val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
                    val callback: NetworkChangeCallback? = null
                    val receiver = object : BroadcastReceiver() {
                        override fun onReceive(context: Context?, intent: Intent?) {
                            val status: Boolean = isNetworkAvailable(context)
                            //Log.e("BroadCast >>>>>", "Status of: " + status)
                            if (status){
                                isNetwork = false
                            }else{
                                isNetwork = true
                            }
                            networkStatus = status
                            callback?.onNetworkChanged(status)
                        }
                        private fun isNetworkAvailable(context: Context?): Boolean {
                            try {
                                val cm =
                                    context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                                val activeNetworkInfo = cm.activeNetworkInfo
                                return (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting)
                            } catch (e: NullPointerException) {
                                // showLog(e.localizedMessage)
                                return false
                            }
                        }
                    }
                    context.registerReceiver(receiver, intentFilter)
                    onDispose {
                        context.unregisterReceiver(receiver)
                    }
                }

                if(networkStatus) {
                    strTitle = ""
                    HomeAppNavGraph()

                }else{
                    if (isNetwork){
                        strTitle =  "Network not Available, please connect the network !"
                        Surface {
                            Column(verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(top = 350.dp, start = 20.dp, end = 20.dp)) {
                                Text(text = strTitle,
                                    color = Color.Red, style = MaterialTheme.typography.titleMedium,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }
                    }
                }
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