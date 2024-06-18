package com.phycare.residentbeacon.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.phycare.residentbeacon.ProvidersDetailsActivity
import com.phycare.residentbeacon.R
import com.phycare.residentbeacon.ResidentViewModel
import com.phycare.residentbeacon.getImgUrl
import com.phycare.residentbeacon.model.PGYItem
import com.phycare.residentbeacon.model.ResidentCompleteSearchItem
import com.phycare.residentbeacon.model.SpecialityItem
import com.phycare.residentbeacon.model.StatesItem
import com.phycare.residentbeacon.ui.theme.BeaconComposeTheme
import java.io.BufferedReader
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvidersScreen(
    navController: NavHostController,
    viewModel: ResidentViewModel,
) {
    BeaconComposeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            viewModel.getAllStates()
            viewModel.getAllPGy()
            viewModel.getAllSpeciality()

            // PostList(movieList)
            PostList(
                viewModel,
                stateList = viewModel.stateListResponse,
                pgyList = viewModel.pgyListResponse,
                specialityList = viewModel.specialityListResponse
            )

        }
    }
}

@Composable
fun stateItem(sItem: StatesItem, index: Int) {
    Log.e("DDDDDDDDD", "value: " + sItem.Location)
    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(text = sItem.Location)
        }

    }
}


@SuppressLint("SuspiciousIndentation")
@ExperimentalMaterial3Api
@Composable
    fun PostList(
        viewModel: ResidentViewModel,
        stateList: List<StatesItem>,
        pgyList: List<PGYItem>,
        specialityList: List<SpecialityItem>,
    ) {
         val context = LocalContext.current
        //get the json data
        val strJson = getJsondataFromAssets(context,"resident.json")
        val gson = Gson()
        val strType = object : TypeToken<List<ResidentCompleteSearchItem>>(){}.type
        val residentCompleteSearchListResponse:List<ResidentCompleteSearchItem> = gson.fromJson(strJson,strType)
        //Log.e("Assets Data","listSize: ${list.size}")

        var showWebView by remember { mutableStateOf(false) }
        var providerName by remember { mutableStateOf("")}
        var locationName by remember { mutableStateOf("")}
        var pgyName by remember { mutableStateOf("")}
        var specialityName by remember { mutableStateOf("")}
        var imgShowAndHide by remember {mutableStateOf(false)}

        if (stateList.size != 0  && specialityList.size != 0) {
            var ResidentName by remember { mutableStateOf("") }
            var isError by rememberSaveable { mutableStateOf(false) }
            var itemsp by remember { mutableStateOf("") }
            var expanded by remember { mutableStateOf(false) }
            var selectedItemIndex by remember { mutableStateOf(0) }
            Column(modifier = Modifier.fillMaxSize()) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(value = ResidentName,
                        onValueChange = {
                            ResidentName = it
                            providerName = ResidentName.toString()
                            showWebView = true
                        },
                        label = { Text(text = "Provider name") },
                        isError = isError,
                        shape = RoundedCornerShape(5.dp),
                        // placeholder = { Text(text = "Enter Username")},
                        modifier = Modifier
                            .padding(
                                start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                            )
                            .weight(1f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                        )
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier
                            .padding(
                                start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                            )
                            .weight(1f)
                    ) {
                        OutlinedTextField(
                            value = stateList[selectedItemIndex].Location,
                            onValueChange = { locationName =  stateList[selectedItemIndex].Location},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor(),
                            shape = RoundedCornerShape(5.dp),
                            label = { Text("Location") },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        )

                        ExposedDropdownMenu(expanded = expanded,
                            onDismissRequest = { expanded = false }) {
                            stateList.forEachIndexed { index, item ->
                                var userCity = item.Location
                                DropdownMenuItem(text = {
                                    Text(
                                        text = item.Location,
                                        fontWeight = if (index == selectedItemIndex) FontWeight.Bold else null
                                    )
                                    itemsp = item.Location
                                }, onClick = {
                                    selectedItemIndex = index
                                    expanded = false
                                    if (item.Location.equals("All")) {
                                        locationName = ""
                                    }
                                    else {
                                        providerName = item.toString()
                                        showWebView = true
                                        locationName = item.Location
                                    }

                                    Log.e("locationName<>>>>>>>>>", "locationName :" + locationName)
                                    //showWebView = true
                                })


                            }
                        }

                    }
                }  // close the row of location

                var expandedPgy by remember { mutableStateOf(false) }
                var selectedItemIndexPgy by remember { mutableStateOf(0) }
                Row(modifier = Modifier.fillMaxWidth()) {
                    ExposedDropdownMenuBox(
                        expanded = expandedPgy,
                        onExpandedChange = { expandedPgy = !expandedPgy },
                        modifier = Modifier
                            .padding(
                                start = 10.dp, end = 10.dp, top = 5.dp, bottom = 1.dp
                            )
                            .weight(1f)
                    ) {
                        OutlinedTextField(
                            value = pgyList[selectedItemIndexPgy].PGY, onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPgy) },
                            modifier = Modifier.menuAnchor(),
                            shape = RoundedCornerShape(5.dp),
                            label = { Text("PGY") },
                            /*colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = Color.Black
                            ),*/
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                            /*   ExposedDropdownMenuDefaults.textFieldColors(
                                   focusedContainerColor = Color.White,
                                   unfocusedContainerColor = Color.White*/

                        )

                        ExposedDropdownMenu(modifier = Modifier
                            .background(Color.White)
                            .exposedDropdownSize(true)
                            .weight(1f),
                            expanded = expandedPgy,
                            onDismissRequest = { expandedPgy = false }) {
                            pgyList.forEachIndexed { index, item ->
                                var userpgy = item.PGY
                                DropdownMenuItem(text = {
                                    Text(
                                        text = item.PGY,
                                        fontWeight = if (index == selectedItemIndexPgy) FontWeight.Bold else null
                                    )
                                    itemsp = item.PGY
                                }, onClick = {
                                    selectedItemIndexPgy = index
                                    expandedPgy = false
                                    if (item.PGY.equals("All")) {
                                        pgyName = ""
                                    } else {
                                        pgyName = item.PGY
                                        showWebView = true
                                    }

                                    Log.e("ListSize<>>>>>>>>>", "Size of :" + pgyName)

                                })

                            }
                        }

                    }
                    //       speciality dropdown -------------- //
                    var expandedspeciality by remember { mutableStateOf(false) }
                    var selectedItemIndexspeciality by remember { mutableStateOf(0) }
                    ExposedDropdownMenuBox(
                        expanded = expandedspeciality,
                        onExpandedChange = { expandedspeciality = !expandedspeciality },
                        modifier = Modifier
                            .padding(
                                start = 10.dp, end = 10.dp, top = 5.dp, bottom = 1.dp
                            )
                            .weight(1f)

                    ) {
                        OutlinedTextField(
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            value = specialityList[selectedItemIndexspeciality].Speciality,
                            onValueChange = {},
                            label = { Text("Speciality") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedspeciality) },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                            textStyle = TextStyle(fontSize = 14.sp)
                        )

                        ExposedDropdownMenu(modifier = Modifier
                            .background(Color.White)
                            .exposedDropdownSize(true)
                            .weight(1f),
                            expanded = expandedspeciality,
                            onDismissRequest = { expandedspeciality = false }) {
                            specialityList.forEachIndexed { index, item ->
                                var userSpeciality = item.Speciality
                                DropdownMenuItem(text = {
                                    Text(
                                        text = item.Speciality,
                                        fontWeight = if (index == selectedItemIndexspeciality) FontWeight.Bold else null
                                    )
                                    itemsp = item.Speciality
                                }, onClick = {
                                    selectedItemIndexspeciality = index
                                    expandedspeciality = false
                                    if (item.Speciality.equals("All")) {
                                        specialityName = ""
                                    } else {
                                        specialityName = item.Speciality
                                        showWebView = true
                                    }
                                    Log.e("ListSize<>>>>>>>>>", "Size of :" + specialityName)
                                    // DisplayListdata(viewModel,providerName,locationName,pgyName)

                                })

                            }
                        }

                    }

                }
                // listview values
                // viewModel.getResidentCompleteSearch(providerName, locationName, pgyName)
               // DisplayListdata(viewModel, providerName, locationName, pgyName)


            }
        } // if close



        if (showWebView) {
            Log.e("name", "providerName: " + providerName)
            Log.e("locationName 2", "locationName 2: " + locationName)
            Log.e("name", "pgyName: " + pgyName)

           // DisplayListdata(viewModel,providerName,locationName,pgyName)


           viewModel.getResidentCompleteSearch(providerName, locationName, pgyName)
            Spacer(modifier = Modifier.height(3.dp))
            // close drop down 2nd
            val visible by remember {
                mutableStateOf(true)
            }
            val animatedAlpha by animateFloatAsState(
                targetValue = if (visible) 1.0f else 0f, label = "alpha"
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp, top = 140.dp)
                /*.graphicsLayer { alpha = animatedAlpha }*/) {
                LazyColumn {
                    if (viewModel.residentCompleteSearchListResponse.size != 0) {
                        itemsIndexed(viewModel.residentCompleteSearchListResponse) { index, item ->

                            Row {
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
                                        .align(Alignment.CenterVertically)
                                        .clip(CircleShape),

                                    )

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .align(Alignment.CenterVertically)
                                ) {
                                    Text(
                                        text = item.Provider_Name.trim(),
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = item.Location.trim(),
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                }
                            }
                            Divider()
                            //}
                        }
                    }
                }

            }
        }else{
           // DisplayListdata(viewModel,providerName,locationName,pgyName)

            viewModel.getResidentCompleteSearch(providerName, locationName, pgyName)
            Spacer(modifier = Modifier.height(3.dp))
            // close drop down 2nd
            val visible by remember {
                mutableStateOf(true)
            }
            val animatedAlpha by animateFloatAsState(
                targetValue = if (visible) 1.0f else 0f, label = "alpha"
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .padding(start = 5.dp, top = 140.dp)
               /* .graphicsLayer { alpha = animatedAlpha }*/) {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()) {
                    if (viewModel.residentCompleteSearchListResponse.size != 0) {
                        itemsIndexed(viewModel.residentCompleteSearchListResponse) { index, item ->
                            Row(modifier = Modifier.clickable {
                                val intent = Intent(context,ProvidersDetailsActivity::class.java)
                                intent.putExtra("ITEM",item)
                                context.startActivity(intent)
                            }) {
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
                                        .align(Alignment.CenterVertically)
                                        .clip(CircleShape),

                                    )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .align(Alignment.CenterVertically)
                                ) {
                                    Text(
                                        text = item.Provider_Name.trim().replace("\n"," "),
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = "Location : "+ item.Location.trim(),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    /*Text(
                                        text = "PGY : "+ item.PGY.trim(),
                                        style = MaterialTheme.typography.titleMedium
                                    )*/

                                }

                            }
                            Divider(modifier = Modifier.padding(start = 55.dp))
                            //}
                        }
                    }
                }

            }
        }



    }

fun getJsondataFromAssets(context: Context, strfile: String): String {
      var string = ""
      string = context.assets.open(strfile).bufferedReader().use { it.readText() }
    return string
}

@Composable
    fun DisplayListdata(

        viewModel: ResidentViewModel,
        providerName: String,
        locationName: String,
        pgyName: String,
    ) {

      //  viewModel.getResidentCompleteSearch(providerName, locationName, pgyName)
        Spacer(modifier = Modifier.height(3.dp))
        // close drop down 2nd
        val visible by remember {
            mutableStateOf(true)
        }
        val animatedAlpha by animateFloatAsState(
            targetValue = if (visible) 1.0f else 0f, label = "alpha"
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(start = 5.dp, top = 140.dp)
            .graphicsLayer { alpha = animatedAlpha }) {
            LazyColumn {
                if (viewModel.residentCompleteSearchListResponse.size != 0) {
                    itemsIndexed(viewModel.residentCompleteSearchListResponse) { index, item ->

                        /*Card(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.LightGray, //Card background color
                               // contentColor = Color.White  //Card content color,e.g.text
                            ),


                            ) {*/
                        Row {
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
                                    .align(Alignment.CenterVertically)
                                    .clip(CircleShape),

                                )

                            /* Image(
                                // painter = painterResource(id = R.drawable.baseline_play_circle_24),
                                 painter = rememberAsyncImagePainter(imageUrl),
                                 contentDescription = "image",
                                 modifier = Modifier
                                     .width(60.dp)
                                     .height(60.dp)
                                     .padding(2.dp)
                                     .align(Alignment.CenterVertically)
                             )*/

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = item.Provider_Name.trim(),
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = item.Location.trim(),
                                    style = MaterialTheme.typography.titleMedium
                                )

                            }
                        }
                        Divider()
                        //}
                    }
                }
            }

        }
    }

