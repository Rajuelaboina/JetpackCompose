package com.phycare.residentbeacon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.phycare.residentbeacon.model.ResidentCompleteSearchItem
import com.phycare.residentbeacon.ui.theme.BeaconComposeTheme

class ProvidersDetailsActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  enableEdgeToEdge()
        setContent {
            BeaconComposeTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
                val navController = rememberNavController()
                BackHandler {
                    navController.popBackStack()
                }
                val item = intent.getSerializableExtra("ITEM") as ResidentCompleteSearchItem
                Log.e("Data from Intent", "Intent Data: " + item.Provider_Name.replace("\n", " "))
                DisplayProviderDetails(item)

            }
        }
    }


}

@Composable
private fun DisplayProviderDetails(item: ResidentCompleteSearchItem) {
    val context = LocalContext.current
    val imageUrl = getImgUrl() + item.Photo
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 0.dp, start = 0.dp)
    ) {

        Box(modifier = Modifier.fillMaxWidth().verticalScroll(state = rememberScrollState(), enabled = true)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painterResource(id = R.drawable.resident_logo),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        val imageModifier = Modifier
                            .size(120.dp)
                            .border(BorderStroke(1.dp, Color.Black))
                            .background(Color.Yellow)
                        /*Image( painterResource(id = R.drawable.doctor),
                            *//*rememberAsyncImagePainter(ContextCompat.getDrawable(context,R.drawable.recanglebox)),*//*
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.TopStart)
                                    .padding(start = 15.dp, top = 25.dp)
                                    .size(150.dp)
                                    .border(BorderStroke(4.dp,Color.White), shape = RoundedCornerShape(20.dp))
                                    *//*.background(color = Color.Transparent,shape = RoundedCornerShape(25.dp))*//*
                            )*/

                        AsyncImage(
                            model = imageUrl, contentDescription = "Resident user Image",
                            placeholder = painterResource(id = R.drawable.doctor),
                            error = painterResource(id = R.drawable.doctor),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(start = 10.dp, top = 20.dp)
                                .border(
                                    BorderStroke(4.dp, Color.White),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .size(150.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .shadow(elevation = 20.dp)
                        )

                    }
                }
                Column(modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 15.dp)) {

                    Text(text = item.Provider_Name, style = MaterialTheme.typography.headlineMedium)
                    Row {
                        if (item.PhoneNo.toString() != "" && item.PhoneNo.toString() != null && item.PhoneNo.toString() != "null") {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_call_24),
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )

                            Text(
                                text = " : " + item.PhoneNo.toString(),
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }

                    }
                    Row {
                        if (item.MailID.toString() != "" && item.MailID.toString() != null && item.MailID.toString() != "null") {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_email_24),
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Text(
                                text = " : " + item.MailID.toString(),
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                    Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                    Text(text = "Program Name : ", style = MaterialTheme.typography.titleMedium)
                    Text(text = item.ProgramName,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 8.dp))
                    Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                    Text(text = "Speciality :  ", style = MaterialTheme.typography.titleMedium)
                    Text(text = item.Speciality,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 8.dp))
                    Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                    Text(text = "Location : " + item.Location,style = MaterialTheme.typography.titleMedium)
                   /* Text(text = item.Location,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 8.dp))*/
                    Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                    Text(text = "Program Location : " + item.ProgramLocation,style = MaterialTheme.typography.titleMedium)
                    Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                    Text(text = "PGY : " + item.PGY,style = MaterialTheme.typography.titleMedium)
                    Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                    Text(text = "Class : " + "\n" +item.ClassOf,style = MaterialTheme.typography.titleMedium)
                    Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                    Text(text = "UnderGraduateCollege : " + "\n" + item.UnderGraduateCollege,style = MaterialTheme.typography.titleMedium)
                    Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                    Text(text = "MedicalSchool : " + "\n" + item.MedicalSchool,style = MaterialTheme.typography.titleMedium)
                    Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                    Text(text = "HomeTown : " + "\n" + item.HomeTown,style = MaterialTheme.typography.titleMedium)

                }


            }


        }
        /* Column {
             val imageUrl = getImgUrl() + item.Photo
             AsyncImage(
                 model = imageUrl, contentDescription = "Resident user Image",
                 placeholder = painterResource(id = R.drawable.doctor),
                 error = painterResource(id = R.drawable.doctor),
                 contentScale = ContentScale.Crop,
                 modifier = Modifier
                     .size(60.dp)*//*.border(
                                                BorderStroke(4.dp, Color.Yellow),
                                                CircleShape
                                            )
                                            .padding(4.dp)*//*
                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape),

                    )
                  Text(text = item.Provider_Name)
                  Text(text = item.PhoneNo.toString())
                  Text(text = item.MailID.toString())
                  Text(text = "Program Name : "+item.ProgramName)
                  Text(text = "Speciality : "+item.Speciality)
                  Text(text = "Location : "+item.Location)
                  Text(text = "Program Location : "+item.ProgramLocation)
                  Text(text = "PGY : "+item.PGY)
                  Text(text = "Class : "+item.ClassOf)
                  Text(text = "UnderGraduateCollege : "+item.UnderGraduateCollege)
                  Text(text = "MedicalSchool : "+item.MedicalSchool)
                  Text(text = "HomeTown : "+item.HomeTown)
            }*/

    }   //

}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BeaconComposeTheme {
        Greeting2("Android")
    }
}