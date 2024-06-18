package com.phycare.residentbeacon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.phycare.residentbeacon.model.ResidentCompleteSearchItem
import com.phycare.residentbeacon.ui.theme.BeaconComposeTheme

class ProvidersDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeaconComposeTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
               val item = intent.getSerializableExtra("ITEM") as ResidentCompleteSearchItem
                Log.e("Data from Intent","Intent Data: "+item.Provider_Name.replace("\n"," "))
               DisplayProviderDetails(item)
            }
        }
    }


}
@Composable
private fun DisplayProviderDetails(item: ResidentCompleteSearchItem) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 15.dp)) {
            Column {
                val imageUrl = getImgUrl() + item.Photo
                AsyncImage(
                    model = imageUrl, contentDescription = "Resident user Image",
                    placeholder = painterResource(id = R.drawable.doctor),
                    error = painterResource(id = R.drawable.doctor),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)/*.border(
                                                BorderStroke(4.dp, Color.Yellow),
                                                CircleShape
                                            )
                                            .padding(4.dp)*/
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
            }

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