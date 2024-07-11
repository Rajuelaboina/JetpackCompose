package com.phycare.residentbeacon.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.phycare.residentbeacon.R
import com.phycare.residentbeacon.ResidentViewModel
import com.phycare.residentbeacon.getImgUrl
import com.phycare.residentbeacon.model.PGYItem
import com.phycare.residentbeacon.model.ResidentCompleteSearchItem
import com.phycare.residentbeacon.model.SpecialityItem
import com.phycare.residentbeacon.model.StatesItem
import com.phycare.residentbeacon.ui.theme.BeaconComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvidersScreen(
   // navController: NavHostController,
    viewModel: ResidentViewModel,
    stateList: SnapshotStateList<StatesItem>,
    pgyList: SnapshotStateList<PGYItem>,
    specialityList: SnapshotStateList<SpecialityItem>,
) {
    BeaconComposeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            /* viewModel.getAllStates()
             viewModel.getAllPGy()
             viewModel.getAllSpeciality()*/

            // PostList(movieList)
            PostList(
                viewModel,
                stateList,
                pgyList,
                specialityList
            )

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
   // val context = LocalContext.current
    //get the json data
   // val strJson = getJsondataFromAssets(context, "resident.json")
   // val gson = Gson()
  //  val strType = object : TypeToken<List<ResidentCompleteSearchItem>>() {}.type
   /* val residentCompleteSearchListResponse: List<ResidentCompleteSearchItem> =
        gson.fromJson(strJson, strType)*/
    //Log.e("Assets Data","listSize: ${list.size}")

    var showWebView by remember { mutableStateOf(false) }
    var providerName by remember { mutableStateOf("") }
    var locationName by remember { mutableStateOf("") }
    var pgyName by remember { mutableStateOf("") }
    var specialityName by remember { mutableStateOf("") }
   // var imgShowAndHide by remember { mutableStateOf(false) }

    if (stateList.isNotEmpty() && specialityList.isNotEmpty()) {
        var residentName by remember { mutableStateOf("") }
        val isError by rememberSaveable { mutableStateOf(false) }
        var itemsp by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        var selectedItemIndex by remember { mutableIntStateOf(0) }
        Column(modifier = Modifier.background(color = Color.White)
            .fillMaxSize(),) {

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = residentName,
                    onValueChange = {
                        residentName = it
                        providerName = residentName
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
                )
                {
                    OutlinedTextField(
                        value = stateList[selectedItemIndex].Location,
                        onValueChange = { locationName = stateList[selectedItemIndex].Location },
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
                          //  var userCity = item.Location
                            DropdownMenuItem(text = {
                                Text(
                                    text = item.Location,
                                    fontWeight = if (index == selectedItemIndex) FontWeight.Bold else null
                                )
                                itemsp = item.Location
                            }, onClick = {
                                selectedItemIndex = index
                                expanded = false
                                if (item.Location == "All") {
                                    locationName = ""
                                } else {
                                    //providerName = item.toString()
                                    showWebView = true
                                    locationName = item.Location
                                }

                               // Log.e("locationName<>>>>>>>>>", "locationName :" + locationName)
                                //showWebView = true
                            })


                        }
                    }

                }
            }  // close the row of location

            var expandedPgy by remember { mutableStateOf(false) }
            var selectedItemIndexPgy by remember { mutableIntStateOf(0) }
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
                           // var userpgy = item.PGY
                            DropdownMenuItem(text = {
                                Text(
                                    text = item.PGY,
                                    fontWeight = if (index == selectedItemIndexPgy) FontWeight.Bold else null
                                )
                                itemsp = item.PGY
                            }, onClick = {
                                selectedItemIndexPgy = index
                                expandedPgy = false
                                if (item.PGY == "All") {
                                    pgyName = ""
                                } else {
                                    pgyName = item.PGY
                                    showWebView = true
                                }

                             //   Log.e("ListSize<>>>>>>>>>", "Size of :" + pgyName)

                            })

                        }
                    }

                }
                //       speciality dropdown -------------- //
                var expandedspeciality by remember { mutableStateOf(false) }
                var selectedItemIndexSpeciality by remember { mutableIntStateOf(0) }
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
                        value = specialityList[selectedItemIndexSpeciality].Speciality,
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
                           // var userSpeciality = item.Speciality
                            DropdownMenuItem(text = {
                                Text(
                                    text = item.Speciality,
                                    fontWeight = if (index == selectedItemIndexSpeciality) FontWeight.Bold else null
                                )
                                itemsp = item.Speciality
                            }, onClick = {
                                selectedItemIndexSpeciality = index
                                expandedspeciality = false
                                if (item.Speciality == "All") {
                                    specialityName = ""
                                } else {
                                    specialityName = item.Speciality
                                    showWebView = true
                                }
                               // Log.e("ListSize<>>>>>>>>>", "Size of :" + specialityName)
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

    /*var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f)
    var currentPosition by remember { mutableStateOf(-1) }*/

    if (showWebView) {

        viewModel.getResidentCompleteSearch(providerName, locationName, pgyName)
        Spacer(modifier = Modifier.height(3.dp))
        // close drop down 2nd
       /* val visible by remember {
            mutableStateOf(true)
        }
        val animatedAlpha by animateFloatAsState(
            targetValue = if (visible) 1.0f else 0f, label = "alpha"
        )*/
        ResponseView(viewModel)

        /* Box(
             modifier = Modifier
                 .fillMaxSize()
                 .padding(start = 5.dp, top = 140.dp)
             *//*.graphicsLayer { alpha = animatedAlpha }*//*
        ) {
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
                                    .size(60.dp)*//*.border(
                                                BorderStroke(4.dp, Color.Yellow),
                                                CircleShape
                                            )
                                            .padding(4.dp)*//*
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

        }*/
    } else {
        viewModel.getResidentCompleteSearch(providerName, locationName, pgyName)
        Spacer(modifier = Modifier.height(3.dp))
        // close drop down 2nd
      /*  val visible by remember { mutableStateOf(true) }
        val animatedAlpha by animateFloatAsState(
            targetValue = if (visible) 1.0f else 0f, label = "alpha"
        )*/

        ResponseView(viewModel)

    }
}

@Composable
fun ResponseView(viewModel: ResidentViewModel) {
    val expandedState by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableIntStateOf(-1) }
    var isShow by rememberSaveable { mutableStateOf(true) }

    if (isShow){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .padding(start = 5.dp, top = 145.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(text = "loading..")
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(start = 5.dp, top = 145.dp)
    )
    {


        if (viewModel.residentCompleteSearchListResponse.isNotEmpty()) {
               isShow = false
            itemsIndexed(viewModel.residentCompleteSearchListResponse)
            { index, item ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth()
                    //.padding(start = 5.dp, top = 140.dp)
                    /* .graphicsLayer { alpha = animatedAlpha }*/
                )
                {
                    Card(modifier = Modifier
                        .selectable(selected = expandedState,
                            onClick = {
                                /* Log.e("currentPO","index:"+index)
                               Log.e("currentPO","po:"+currentPosition)
                               if (currentPosition != index) {
                                   //  expandedState = !expandedState
                                   currentPosition = index
                               } else {
                                   currentPosition = -1
                               }*/
                            }
                        )
                        .fillMaxSize()
                        .fillMaxWidth()
                        .padding(3.dp)
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            )
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(10.dp)

                    )
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .fillMaxWidth()
                        )
                        {
                            Row(modifier = Modifier
                                .clickable {
                                    /* val intent = Intent(context, ProvidersDetailsActivity::class.java)
                                     intent.putExtra("ITEM", item)
                                     context.startActivity(intent)*/

                                    currentPosition = if (currentPosition != index) {
                                        //  expandedState = !expandedState
                                        index
                                    } else {
                                        -1
                                    }

                                })
                            {

                                if (currentPosition != index) {
                                    val imageUrl = getImgUrl() + item.Photo
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Resident user Image",
                                        placeholder = painterResource(id = R.drawable.doctor),
                                        error = painterResource(id = R.drawable.doctor),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .align(Alignment.CenterVertically)
                                            .clip(CircleShape),
                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                    {
                                        Text(
                                            text = item.Provider_Name.trim().replace("\n", " "),
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        Text(
                                            text = "Location : " + item.Location.trim(),
                                            style = MaterialTheme.typography.titleMedium
                                        )

                                    }
                                } else {
                                    FullView(item)
                                }

                            }

                        }
                        //    Divider(modifier = Modifier.padding(start = 55.dp))

                    }
                }
            }
        }
    }
}

@Composable
fun FullView(item: ResidentCompleteSearchItem) {
    Column(modifier = Modifier.padding(start = 2.dp, top = 2.dp, end = 12.dp)) {

        Row(modifier = Modifier.fillMaxWidth()) {
            val imageUrl = getImgUrl() + item.Photo
            AsyncImage(
                model = imageUrl, contentDescription = "Resident user Image",
                placeholder = painterResource(id = R.drawable.doctor),
                error = painterResource(id = R.drawable.doctor),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterVertically)
                    .clip(CircleShape),
            )
            Text(
                text = item.Provider_Name,
                modifier = Modifier.padding(start = 5.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Column(modifier = Modifier.padding(start = 10.dp)) {


            Row {
                if (item.PhoneNo.toString() != "" && item.PhoneNo.toString() != "null") {
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
                if (item.MailID.toString() != "" && item.MailID.toString() != "null") {
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
            Text(
                text = item.ProgramName,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            Text(text = "Speciality :  ", style = MaterialTheme.typography.titleMedium)
            Text(
                text = item.Speciality,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            Text(text = "Location : " + item.Location, style = MaterialTheme.typography.titleMedium)
            /* Text(text = item.Location,
                 style = MaterialTheme.typography.titleMedium,
                 modifier = Modifier.padding(start = 8.dp))*/
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            Text(
                text = "Program Location : " + item.ProgramLocation,
                style = MaterialTheme.typography.titleMedium
            )
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            Text(text = "PGY : " + item.PGY, style = MaterialTheme.typography.titleMedium)
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            Text(
                text = "Class : " + "\n" + item.ClassOf,
                style = MaterialTheme.typography.titleMedium
            )
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            Text(
                text = "UnderGraduateCollege : " + "\n" + item.UnderGraduateCollege,
                style = MaterialTheme.typography.titleMedium
            )
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            Text(
                text = "MedicalSchool : " + "\n" + item.MedicalSchool,
                style = MaterialTheme.typography.titleMedium
            )
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            Text(
                text = "HomeTown : " + "\n" + item.HomeTown,
                style = MaterialTheme.typography.titleMedium
            )

        }

    }
}

fun getJsondataFromAssets(context: Context, strfile: String): String {
    val string: String = context.assets.open(strfile).bufferedReader().use { it.readText() }
    return string
}

