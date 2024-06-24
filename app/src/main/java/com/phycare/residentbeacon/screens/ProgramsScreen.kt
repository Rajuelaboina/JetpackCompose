package com.phycare.residentbeacon.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    val context = LocalContext.current
    //get the json data
    val strJson = getJsondataFromAssets(context,"program.json")
    val gson = Gson()
    val strType = object : TypeToken<List<CompleteProgramSearchItem>>(){}.type
    val programComSearchList:List<CompleteProgramSearchItem> = gson.fromJson(strJson,strType)
    //Log.e("Assets Data","listSize: ${list.size}")

    var showWebView by remember { mutableStateOf(false) }
    var programName by remember { mutableStateOf("") }
    var locationName by remember { mutableStateOf("") }
    var specialityName by remember { mutableStateOf("") }

    if (stateList.size != 0 && specialityList.size != 0) {
        var ProgramName by remember { mutableStateOf("") }
        var isError by rememberSaveable { mutableStateOf(false) }
        var itemsp by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        var selectedItemIndex by remember { mutableStateOf(0) }
        Column(modifier = Modifier.fillMaxSize()) {

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = ProgramName,
                    onValueChange = {
                        ProgramName = it
                        programName = ProgramName
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
                        .weight(1f)
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
                                } else {
                                    showWebView = true
                                    locationName = item.Location
                                }

                                Log.e("locationName<>>>>>>>>>", "locationName :" + locationName)
                                //showWebView = true
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
            } // row close


        }

    } //if close
    if (showWebView) {
        Log.e("name", "providerName: " + programName)
        Log.e("name", "locationName 2: " + locationName)
        Log.e("name", "specialityName: " + specialityName)
        viewModel.getProgramCompleteSearchData(programName, locationName, specialityName)
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
            .padding(start = 5.dp, top = 80.dp)
            /*.graphicsLayer { alpha = animatedAlpha }*/) {
            LazyColumn {
                if (viewModel.programComSearchListResponse.size != 0) {
                    itemsIndexed(viewModel.programComSearchListResponse) { index, item ->

                        Row {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = item.ProgramName.trim(),
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = item.Speciality.trim(),
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
    else{
        viewModel.getProgramCompleteSearchData(programName,locationName,specialityName)
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
            .padding(start = 5.dp, top = 80.dp)
            /*.graphicsLayer { alpha = animatedAlpha }*/) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()) {
                if (/*viewModel.programComSearchListResponse.size*/ programComSearchList.size != 0) {
                    itemsIndexed(/*viewModel.programComSearchListResponse*/ programComSearchList) { index, item ->

                        Row(modifier = Modifier.clickable {
                           /* val intent = Intent(context, ProvidersDetailsActivity::class.java)
                            intent.putExtra("ITEM",item)
                            context.startActivity(intent)*/
                        }) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = item.ProgramName.trim(),
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = "Speciality : "+ item.Speciality.trim(),
                                    style = MaterialTheme.typography.titleMedium
                                )


                            }

                        }
                        Divider()
                        //  15+10+10=25
                    }
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun DrawerAppComponentPreview() {
    val  viewModel = ResidentViewModel()
    BottomNavigationBar(viewModel)
}
