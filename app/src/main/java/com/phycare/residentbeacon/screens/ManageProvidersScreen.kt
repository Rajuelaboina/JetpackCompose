package com.phycare.residentbeacon.screens


import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.phycare.residentbeacon.AddUserActivity
import com.phycare.residentbeacon.AllDestinations
import com.phycare.residentbeacon.R
import com.phycare.residentbeacon.ResidentViewModel
import com.phycare.residentbeacon.Screens
import com.phycare.residentbeacon.model.PGYItem
import com.phycare.residentbeacon.model.ResidentCompleteSearchItem
import com.phycare.residentbeacon.model.SpecialityItem
import com.phycare.residentbeacon.model.StatesItem
import com.phycare.residentbeacon.ui.theme.BeaconComposeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProvidersScreen(
    navController: NavHostController,
    viewModel: ResidentViewModel,
    stateList: SnapshotStateList<StatesItem>,
    pgyList: SnapshotStateList<PGYItem>,
    specialityList: SnapshotStateList<SpecialityItem>,
)
{
    BeaconComposeTheme {
        val isFloatingActionButtonDocked by remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        Scaffold(

            floatingActionButton = {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        context.startActivity(Intent(context,AddUserActivity::class.java))

                       // navController.navigate(AllDestinations.PROGRAMS)
                       //
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add , contentDescription ="Image" )
                  }
            }, floatingActionButtonPosition = FabPosition.End,

            ) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
              /*  viewModel.getAllStates()
                viewModel.getAllPGy()
                viewModel.getAllSpeciality()*/

                // PostList(movieList)
                manageProvidersList(
                    viewModel,
                    stateList /*= viewModel.stateListResponse*/,
                    pgyList /*= viewModel.pgyListResponse*/,
                    specialityList /*= viewModel.specialityListResponse*/
                )

            }
             }
        /*Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            viewModel.getAllStates()
            viewModel.getAllPGy()
            viewModel.getAllSpeciality()

            // PostList(movieList)
            manageProvidersList(
                viewModel,
                stateList = viewModel.stateListResponse,
                pgyList = viewModel.pgyListResponse,
                specialityList = viewModel.specialityListResponse
            )

        }*/
        /*Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = { TODO }
                ) {
                    Icon(imageVector = Icons.Default.Add , contentDescription ="Image" )
                  }
            }, floatingActionButtonPosition = FabPosition.End,

            ) {
                   // body
             }*/
    }

}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun manageProvidersList(
    viewModel: ResidentViewModel,
    stateList: SnapshotStateList<StatesItem>,
    pgyList: SnapshotStateList<PGYItem>,
    specialityList: SnapshotStateList<SpecialityItem>,
) {
    val context = LocalContext.current
    //get the json data
    val strJson = getJsondataFromAssets(context,"resident.json")
    val gson = Gson()
    val strType = object : TypeToken<List<ResidentCompleteSearchItem>>(){}.type
    val residentCompleteSearchListResponse:List<ResidentCompleteSearchItem> = gson.fromJson(strJson,strType)
    //Log.e("Assets Data","listSize: ${list.size}")

    var showWebView by remember { mutableStateOf(false) }
    var providerName by remember { mutableStateOf("") }
    var locationName by remember { mutableStateOf("") }
    var pgyName by remember { mutableStateOf("") }
    var specialityName by remember { mutableStateOf("") }
    var imgShowAndHide by remember { mutableStateOf(false) }

    val notesList = remember {
        mutableStateListOf<ResidentCompleteSearchItem>()
    }
    val inputvalue = remember { mutableStateOf(TextFieldValue()) }

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
                                   // providerName =""
                                    showWebView = true
                                    locationName = item.Location
                                }

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

        }
    } // if close

    var isEnabled by remember {  mutableStateOf(false) }
    var selectedItems by remember { mutableStateOf<Set<Int>>(emptySet())}
    var selectAll by remember { mutableStateOf(false) }
    viewModel.getResidentCompleteSearch(providerName, locationName, pgyName)
    LaunchedEffect(key1 = selectAll) {
        selectedItems = if ( selectAll) selectedItems.plus(0..viewModel.residentCompleteSearchListResponse.size) else selectedItems.minus(0..viewModel.residentCompleteSearchListResponse.size)

    }

    if (showWebView) {
        /*Log.e("name", "providerName: " + providerName)
        Log.e("name", "locationName 2: " + locationName)
        Log.e("name", "pgyName: " + pgyName) */
        var isShow by rememberSaveable { mutableStateOf(true) }
        if (isShow){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(start = 5.dp, top = 150.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = "loading..")
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        // close drop down 2nd
        val visible by remember {mutableStateOf(true)  }
        val animatedAlpha by animateFloatAsState(
            targetValue = if (visible) 1.0f else 0f, label = "alpha"
        )

        Column {
            viewModel.getResidentCompleteSearch(providerName, locationName, pgyName)
            isShow = false
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, top = 150.dp)
                    .fillMaxWidth()
                    .toggleable(
                        value = selectAll,
                        onValueChange = {
                            selectAll = it
                            isEnabled = it
                        },
                        role = Role.Checkbox
                    )
            ) {
                Text(text = "Select All : "+viewModel.residentCompleteSearchListResponse.size, modifier = Modifier.weight(6f))

              //  if (isEnabled){
                    Image(painterResource(id = R.drawable.baseline_delete_24), contentDescription = "", modifier = Modifier.align(Alignment.CenterVertically))
              //  }

                Checkbox(
                    checked = selectAll, onCheckedChange = null, modifier = Modifier
                        .weight(1f)
                        .padding(top = 5.dp, end = 1.dp)
                )

                // heading view
                val modifier = Modifier
                //HeadingView(viewModel,selectAll,isEnabled,modifier)

            }

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp, top = 1.dp)
                /*.graphicsLayer { alpha = animatedAlpha }*/) {


                LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                    if (viewModel.residentCompleteSearchListResponse.size != 0) {
                        itemsIndexed(viewModel.residentCompleteSearchListResponse){ index, item ->
                            EachRow(
                                isEnabled = isEnabled,
                                item = item,
                                index = index,
                                selectedItem = selectedItems.contains(index),
                                onClick = {
                                    selectedItems =
                                        if (selectedItems.contains(index)) selectedItems.minus(index)
                                        else selectedItems.plus(index)
                                },

                                onEnableChange = { value ->
                                    isEnabled = value
                                },
                            )
                        }
                    }
                }
            }
        }


    }else{
        var isShow by rememberSaveable { mutableStateOf(true) }
        if (isShow){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(start = 5.dp, top = 150.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = "loading..")
            }
        }
        Spacer(modifier = Modifier.height(3.dp))

        // close drop down 2nd
        var deleteList by remember {mutableStateOf(false)}
        val visible by remember {mutableStateOf(true)}
        val animatedAlpha by animateFloatAsState(targetValue = if (visible) 1.0f else 0f, label = "alpha")
        viewModel.getResidentCompleteSearch(providerName, locationName, pgyName)
        val list = ArrayList<ResidentCompleteSearchItem>()
        list.addAll(viewModel.residentCompleteSearchListResponse)

        Column {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, top = 150.dp)
                    .fillMaxWidth()
                    .toggleable(
                        value = selectAll,
                        onValueChange = {
                            selectAll = it
                            isEnabled = it
                        },
                        role = Role.Checkbox
                    )
            ) {
                isShow = false
                Text(text = "Select All : " + list.size, modifier = Modifier.weight(6f))
            //    if (isEnabled){
                    Image(painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clickable {
                                Toast
                                    .makeText(context, "Delete", Toast.LENGTH_LONG)
                                    .show()
                                list.clear()
                                deleteList = true
                                selectAll = false
                            }
                    )
                Checkbox(checked = selectAll, onCheckedChange = null,modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp, end = 1.dp))
            }

            if (!deleteList) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth()
                        .padding(start = 5.dp, top = 15.dp)
                    /* .graphicsLayer { alpha = animatedAlpha }*/
                ) {
                    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                        itemsIndexed(list) { index, item ->
                            EachRow(
                                isEnabled = isEnabled,
                                item = item,
                                index = index,
                                selectedItem = selectedItems.contains(index),
                                onClick = {
                                    selectedItems =
                                        if (selectedItems.contains(index)) selectedItems.minus(index)
                                        else selectedItems.plus(index)
                                },

                                onEnableChange = { value ->
                                    isEnabled = value
                                },
                            )
                        }
                    }
                } // close Box
            }
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EachRow(
    modifier: Modifier = Modifier,
    item: ResidentCompleteSearchItem,
    isEnabled: Boolean,
    selectedItem: Boolean,
    onClick: () -> Unit,
    onEnableChange: (Boolean) -> Unit,
    index: Int
) {

    Box(
        modifier = modifier
            .padding(start = 5.dp, top = 2.dp)
            /*.background(Color.LightGray, RoundedCornerShape(4.dp))*/
            .fillMaxSize()
            .combinedClickable(onLongClick = {
                onEnableChange(true)  //
            }, onClick = onClick)
    ) {
        //if (isEnabled)
            Column(modifier = Modifier.fillMaxSize()) {


                Text(
                    text = item.Provider_Name.trim().replace("\n", " "),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier
                        .padding(start = 0.dp, top = 8.dp)
                )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)) {

                    Text(
                        text = "Location : " + item.Location,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .weight(6f)
                            .align(Alignment.CenterVertically)
                    )

                    Checkbox(
                        checked = selectedItem, onCheckedChange = {null
                        }, modifier = Modifier
                            .padding(0.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically)

                    )
                }

                Text(
                    text = "PGY : " + item.PGY,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "Status : " + "Active",
                    style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 4.dp)
                )





        }
        Divider()
    }
}


