package com.phycare.residentbeacon.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.phycare.residentbeacon.BottomNavigationBar
import com.phycare.residentbeacon.ResidentViewModel
import com.phycare.residentbeacon.model.CompleteProgramSearchItem
import com.phycare.residentbeacon.model.PGYItem
import com.phycare.residentbeacon.model.SpecialityItem
import com.phycare.residentbeacon.model.StatesItem

@Composable
fun ProgramsScreen(
    navController: NavHostController,
    viewModel: ResidentViewModel,
    stateList: SnapshotStateList<StatesItem>,
    pgyList: SnapshotStateList<PGYItem>,
    specialityList: SnapshotStateList<SpecialityItem>,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
       /* viewModel.getAllStates()
        viewModel.getAllPGy()
        viewModel.getAllSpeciality()
*/

        ProgramListDisplay(
            viewModel,
            stateList ,
            specialityList
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgramListDisplay(
    viewModel: ResidentViewModel,
    stateList: List<StatesItem>,
    specialityList: List<SpecialityItem>,
) {
   // val context = LocalContext.current
    //get the json data
   /* val strJson = getJsondataFromAssets(context,"program.json")
    val gson = Gson()
    val strType = object : TypeToken<List<CompleteProgramSearchItem>>(){}.type
    val programComSearchList:List<CompleteProgramSearchItem> = gson.fromJson(strJson,strType)*/
    //Log.e("Assets Data","listSize: ${list.size}")

    var showWebView by remember { mutableStateOf(false) }
    var programName by remember { mutableStateOf("") }
    var locationName by remember { mutableStateOf("") }
    var specialityName by remember { mutableStateOf("") }

    if (stateList.isNotEmpty() && specialityList.isNotEmpty()) {
        //var programName by remember { mutableStateOf("") }
        val isError by rememberSaveable { mutableStateOf(false) }
        var itemsp by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        var selectedItemIndex by remember { mutableIntStateOf(0) }
        Column(modifier = Modifier.background(color = Color.White).fillMaxSize()) {

            Row(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
                OutlinedTextField(
                    value = programName,
                    onValueChange = {
                        programName = it
                        showWebView = true
                    },
                    label = { Text(text = "Program name")},
                    isError = isError,
                    shape = RoundedCornerShape(5.dp),
                    // placeholder = { Text(text = "Enter Username")},
                    textStyle = TextStyle(fontSize = 12.sp),
                    modifier = Modifier
                        .padding(
                            start = 8.dp, end = 2.dp, top = 10.dp, bottom = 1.dp
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
                            start = 5.dp, end = 2.dp, top = 10.dp, bottom = 1.dp
                        )
                        .weight(1f).align(Alignment.CenterVertically)
                ) {
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
                           // var userCity = item.Location
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
                                    showWebView = true
                                    locationName = item.Location
                                }

                               // Log.e("locationName<>>>>>>>>>", "locationName :" + locationName)
                                //showWebView = true
                            })


                        }
                    }

                }

                //       speciality dropdown -------------- //
                var expandedspeciality by remember { mutableStateOf(false) }
                var selectedItemIndexspeciality by remember { mutableIntStateOf(0) }
                ExposedDropdownMenuBox(
                    expanded = expandedspeciality,
                    onExpandedChange = { expandedspeciality = !expandedspeciality },
                    modifier = Modifier
                        .padding(
                            start = 10.dp, end = 10.dp, top = 5.dp, bottom = 1.dp
                        )
                        .weight(1f).align(Alignment.CenterVertically)

                ) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        readOnly = true,
                        value = specialityList[selectedItemIndexspeciality].Speciality,
                        onValueChange = {},
                        label = { Text("Speciality", maxLines = 1) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedspeciality) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),

                    )

                    ExposedDropdownMenu(modifier = Modifier
                        .background(Color.White)
                        .exposedDropdownSize(true)
                        .weight(1f),
                        expanded = expandedspeciality,
                        onDismissRequest = { expandedspeciality = false }) {
                        specialityList.forEachIndexed { index, item ->
                          //  var userSpeciality = item.Speciality
                            DropdownMenuItem(text = {
                                Text(
                                    text = item.Speciality,
                                    fontWeight = if (index == selectedItemIndexspeciality) FontWeight.Bold else null
                                )
                                itemsp = item.Speciality
                            }, onClick = {
                                selectedItemIndexspeciality = index
                                expandedspeciality = false
                                if (item.Speciality == "All") {
                                    specialityName = ""
                                } else {
                                    specialityName = item.Speciality
                                    showWebView = true
                                }
                                //.e("ListSize<>>>>>>>>>", "Size of :" + specialityName)
                                // DisplayListdata(viewModel,providerName,locationName,pgyName)

                            })

                        }
                    }

                }
            } // row close


        }

    } //if close
    if (showWebView) {
       /* Log.e("name", "providerName: " + programName)
        Log.e("name", "locationName 2: " + locationName)
        Log.e("name", "specialityName: " + specialityName)*/
        viewModel.getProgramCompleteSearchData(programName, locationName, specialityName)
        //Spacer(modifier = Modifier.height(0.dp))
        // close drop down 2nd
        UserProgramView(viewModel)

    }
    else{
        viewModel.getProgramCompleteSearchData(programName,locationName,specialityName)
        //Spacer(modifier = Modifier.height(3.dp))
        // close drop down 2nd
        /*val visible by remember {
            mutableStateOf(true)
        }
        val animatedAlpha by animateFloatAsState(
            targetValue = if (visible) 1.0f else 0f, label = "alpha"
        )*/
        UserProgramView(viewModel)

    }

}
@Composable
fun UserProgramView(viewModel: ResidentViewModel) {
   // val expandedState by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableIntStateOf(-1) }
    var isShow by rememberSaveable { mutableStateOf(true) }
    if (isShow){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .padding(start = 5.dp, top = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(text = "loading..")
        }
    }
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .fillMaxWidth()
        .padding(start = 5.dp, top = 90.dp))
    {
        if (viewModel.programComSearchListResponse.isNotEmpty())
        {
            isShow = false
            itemsIndexed(viewModel.programComSearchListResponse) { index, item ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()

                ){
                    Card(modifier = Modifier
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
                        Column(modifier = Modifier
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

                            }
                            .fillMaxSize()
                            .fillMaxWidth()
                        )
                        {
                            if (currentPosition != index) {
                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start=10.dp, bottom = 5.dp)
                                    .align(Alignment.CenterHorizontally)
                                ) {
                                    Text(
                                        text = "Program Name : ",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = item.programName.trim(),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "Speciality : ",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = item.speciality.trim(),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "Location : ",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = item.location.trim(),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                                Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))

                            }else{
                                 FullView(item)
                            }
                        }
                    }
                }
            }
        }
    } //LazyColumn close
}

@Composable
fun FullView(item: CompleteProgramSearchItem) {
      Column(modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp).background(Color.LightGray )) {
          Text(
              text = "Program Id " ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.programID.toString(),
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "Program Name" ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.programName,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "Speciality" ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.speciality,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "Location" ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.location,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "AdminInfo" ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.adminInfo,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "ContactInfo" ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.contactInfo,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "Program Link " ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.programLink,
              style = MaterialTheme.typography.titleMedium
          )
          /*Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "Residency Link",
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text = item.residencyLink,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
             text = " Last Updated " ,
             style = MaterialTheme.typography.titleLarge
         )
         Text(
             text =  item.lastUpdated,
             style = MaterialTheme.typography.titleMedium
         )
         Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            Text(
                text = "Survey Received" ,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text =  item.surveyReceived,
                style = MaterialTheme.typography.titleMedium
            )
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            Text(
                text = "Location info " ,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text =  item.locationInfo,
                style = MaterialTheme.typography.titleMedium
            )
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            Text(
                text = "Sponsor " ,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text =  item.sponsor,
                style = MaterialTheme.typography.titleMedium
            )
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "Participant1 " ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.participant1,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))*/

          /*Text(
              text = "Participant2" ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.participant2,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))*/
         /* Text(
              text = "Participant3" ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.participant3,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))*/
         /* Text(
              text = "Program type " ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.programType,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "Program Affiliation" ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.programAffiliation,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = " Accredited Years" ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.accreditedYears.toString(),
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "Required Years " ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.requiredYears.toString(),
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = " Accepting Applications " ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.acceptingApplications,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "Accepting Next Year " ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.acceptingNextYear,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "Starting Date " ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.startingDate,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "ERAS Participant " ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.eRASParticipant,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "Government Affiliated " ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.governmentAffiliated,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
          Text(
              text = "Additional Comments " ,
              style = MaterialTheme.typography.titleLarge
          )
          Text(
              text =  item.additionalComments,
              style = MaterialTheme.typography.titleMedium
          )
          Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))*/

      }

}

@Preview(showBackground = true)
@Composable
fun DrawerAppComponentPreview() {
    val  viewModel = ResidentViewModel()
   // BottomNavigationBar(viewModel, Modifier)
}
