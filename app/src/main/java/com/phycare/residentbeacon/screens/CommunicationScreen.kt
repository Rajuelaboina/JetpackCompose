package com.phycare.residentbeacon.screens

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.phycare.residentbeacon.ResidentViewModel
import com.phycare.residentbeacon.model.PGYItem
import com.phycare.residentbeacon.model.ResidentCompleteSearchItem
import com.phycare.residentbeacon.model.SpecialityItem
import com.phycare.residentbeacon.model.StatesItem


@Composable
fun CommunicationScreen(
    navController: NavHostController,
    viewModel: ResidentViewModel,
    stateList: SnapshotStateList<StatesItem>,
    pgyList: SnapshotStateList<PGYItem>,
    specialityList: SnapshotStateList<SpecialityItem>
) {

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
       /* viewModel.getAllStates()
        viewModel.getAllPGy()
        viewModel.getAllSpeciality()*/

        // PostList(movieList)
        displayScreen(
            viewModel,
            stateList /*= viewModel.stateListResponse*/,
            pgyList /*= viewModel.pgyListResponse*/,
            specialityList /*= viewModel.specialityListResponse*/
        )



    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun displayScreen(
    viewModel: ResidentViewModel,
    stateList: SnapshotStateList<StatesItem>,
    pgyList: SnapshotStateList<PGYItem>,
    specialityList: SnapshotStateList<SpecialityItem>,
) {
    val context = LocalContext.current
    val emailIntent = Intent(Intent.ACTION_SEND)
    var fileName by remember { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result of the activity here
        // For example, you can retrieve data from the activity result
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val uri: Uri? = data?.data
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri)
            Log.e("URI DATA", "URI : $uri")
            val cursor: Cursor? = context.getContentResolver().query(uri!!, null, null, null, null)
            val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = cursor?.getColumnIndex(OpenableColumns.SIZE)
            cursor?.moveToFirst()
            fileName = cursor!!.getString(nameIndex!!)
            Log.e("URI DATA", "URI2 : ${fileName}")
        }
        // Handle the data accordingly
    }


    var getDataSearch by remember { mutableStateOf(false) }
    var locationName by remember { mutableStateOf("") }
    var pgyName by remember { mutableStateOf("") }
    var specialityName by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    val statusList = mutableListOf("All", "Active", "Inactive")
    var completeList = mutableListOf<ResidentCompleteSearchItem>()


    var isOneTime by remember { mutableStateOf(true) }
    var selectAll by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(false) }
    var selectedItems by remember { mutableStateOf<Set<Int>>(emptySet()) }

    var subTxt by remember { mutableStateOf("") }
    var bodyTxt by remember { mutableStateOf("") }

    var listCount = remember { mutableStateListOf<String>() }

    val resultList = remember { mutableListOf<ResidentCompleteSearchItem>() }

    val alert = remember {mutableStateOf(false)}
    var imagePickerLauncher: ActivityResultLauncher<Intent>
    var imagePickerResultHandler: ((Uri) -> Unit)? = null


    if (stateList.size != 0 && specialityList.size != 0) {
        var ResidentName by remember { mutableStateOf("") }
        var isError by rememberSaveable { mutableStateOf(false) }
        var itemsp by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        var selectedItemIndex by remember { mutableStateOf(0) }
        var loader by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()

        )
        {

            /*if(loader){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }*/


            Row(modifier = Modifier.fillMaxWidth()) {
                var expandedPgy by remember { mutableStateOf(false) }
                var selectedItemIndexPgy by remember { mutableStateOf(0) }

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
                                    locationName = item.Location

                                }
                                getDataSearch = true
                                getResponseData(viewModel,locationName,pgyName,specialityName)
                            })


                        }
                    }

                }
                // pgy
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

                                }
                                getDataSearch = true

                            })

                        }
                    }

                }
            }  // close the row of location


            Row(modifier = Modifier.fillMaxWidth()) {

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

                            DropdownMenuItem(text = {
                                Text(
                                    text = item.Speciality, maxLines = 1,
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

                                }
                                getDataSearch = true

                            })

                        }
                    }
                }
                // status
                var expandedstatus by remember { mutableStateOf(false) }
                var selectedItemIndexstatus by remember { mutableStateOf(0) }
                ExposedDropdownMenuBox(
                    expanded = expandedstatus,
                    onExpandedChange = { expandedstatus = !expandedstatus },
                    modifier = Modifier
                        .padding(
                            start = 10.dp, end = 10.dp, top = 5.dp, bottom = 1.dp
                        )
                        .weight(1f)

                ) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        readOnly = true,
                        value = statusList[selectedItemIndexstatus],
                        onValueChange = {},
                        label = { Text("Status") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedstatus) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        textStyle = TextStyle(fontSize = 14.sp)
                    )

                    ExposedDropdownMenu(modifier = Modifier
                        .background(Color.White)
                        .exposedDropdownSize(true)
                        .weight(1f),
                        expanded = expandedstatus,
                        onDismissRequest = { expandedstatus = false }) {
                        statusList.forEachIndexed { index, item ->

                            DropdownMenuItem(text = {
                                Text(
                                    text = item, maxLines = 1,
                                    fontWeight = if (index == selectedItemIndexstatus) FontWeight.Bold else null
                                )
                                itemsp = item
                            }, onClick = {
                                selectedItemIndexstatus = index
                                expandedspeciality = false
                                if (item.equals("All")) {
                                    status = ""
                                } else {
                                    status = item

                                    getDataSearch = true
                                }
                            })
                        }
                    }
                } // drop down status

            }  // row of 2nd


        } // column
    } //if close
    if (getDataSearch) {
        isOneTime = true
        Log.e("name", "locationName: " + locationName)
        Log.e("name", "pgyName: " + pgyName)
        Log.e("name", "providerName: " + specialityName)
        viewModel.getResidentCompleteSearch(locationName, pgyName, specialityName)
        Spacer(modifier = Modifier.height(3.dp))
        viewModel.getResidentCompleteSearchFilter(locationName, pgyName, specialityName)

       // Log.e("resultList size", "resultList size: " + viewModel.residentCompleteSearchList.size)
        if (isOneTime) {
            viewModel.residentCompleteSearchListResponse.forEach {

                if (it.MailID != null && !it.MailID.isEmpty()) {
                    resultList.addAll(listOf(it))
                }
            }
        }

        isOneTime = false
        // size of title
        var sizeCount = resultList.size
        var cc by remember { mutableStateOf(sizeCount) }

        //Log.e("resultList","ResultList: ${resultList.size}")
        LaunchedEffect(key1 = selectAll) {
            selectedItems =
                if (selectAll) selectedItems.plus(0..resultList.size) else selectedItems.minus(0..resultList.size)

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp, top = 150.dp)
                .verticalScroll(state = rememberScrollState(), enabled = true)
        )
        {
            Column {


                Row(
                    modifier = Modifier.toggleable(
                        value = selectAll,
                        onValueChange = {
                            selectAll = it
                            isEnabled = it
                            Log.e("All", "All:")
                            if (isEnabled) {
                                listCount.clear()
                                cc = 0
                                resultList.forEach {
                                    listCount.add(it.MailID.toString())
                                }

                            } else {
                                cc = sizeCount
                                listCount.clear()
                            }
                        },
                        role = Role.Checkbox
                    ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        text = "Available : $cc",
                        modifier = Modifier.padding(start = 15.dp)
                    )
                    Text(
                        text = "Selected : ${listCount.size}",
                        modifier = Modifier.padding(start = 55.dp)
                    )
                    Text(
                        text = "All",
                        modifier = Modifier.padding(start = 55.dp)
                    )
                    Checkbox(
                        checked = selectAll,
                        onCheckedChange = null,
                        modifier = Modifier
                            .padding(end = 0.dp, start = 0.dp)
                            .weight(8f)
                    )
                }


                Spacer(modifier = Modifier.padding(top = 8.dp))
                LazyVerticalGrid(columns = GridCells.Fixed(1), modifier = Modifier.height(350.dp))
                {
                    itemsIndexed(resultList) { index, item ->

                        EachRow2(
                            isEnabled = isEnabled,
                            item = item,
                            index = index,
                            selectedItem = selectedItems.contains(index),
                            onClick = {
                                selectedItems =
                                    if (selectedItems.contains(index)) {
                                        listCount.remove(item.MailID.toString())
                                        Log.e(
                                            "SIZE COUNT",
                                            "listCoSize:  ${item.MailID.toString()}"
                                        )
                                        // Log.e("SIZE COUNT","sizeCount:  ${sizeCount}")
                                        cc = sizeCount.minus(listCount.size)
                                        selectedItems.minus(index)
                                    } else {
                                        listCount.add(item.MailID.toString())
                                        Log.e("SIZE COUNT", "listCoSize:  ${listCount.size}")
                                        cc = sizeCount.minus(listCount.size)
                                        selectedItems.plus(index)

                                    }
                                /* listCount.add(item.MailID.toString())
                                 Log.e("SIZE COUNT","listCoSize:  ${listCount.size}")
                                 cc = sizeCount.minus(listCount.size)
                                 Log.e("SIZE COUNT"," $cc")*/


                            },

                            onEnableChange = { value ->
                                isEnabled = value
                            },
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(start = 15.dp, top = 3.dp)
                )
                {

                    OutlinedTextField(
                        value = subTxt,
                        onValueChange = { subTxt = it },
                        placeholder = { Text(text = "Subject") },
                        modifier = Modifier
                            .padding(bottom = 3.dp, end = 15.dp)
                            .fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = bodyTxt,
                        onValueChange = { bodyTxt = it },
                        placeholder = { Text(text = "Body") },
                        modifier = Modifier
                            .padding(bottom = 3.dp, end = 15.dp)
                            .fillMaxWidth()

                    )


                }
                Row(modifier = Modifier.padding(start = 15.dp, bottom = 5.dp))
                {
                    Button(
                        modifier = Modifier.padding(end = 3.dp),
                        onClick = {
                            // loader = !loader
                        }
                    )
                    {
                        Text(text = "Choose file")
                    }
                    Button(
                        onClick = {


                            val emailAddress = arrayOfNulls<String>(listCount.size)
                            // emailAddress[0] = "raju.elaboina@gmail.com"
                            for (i in 0 until listCount.size) {
                                emailAddress[i] = listCount[i]
                                Log.e("EEEEEE", listCount[i])
                            }
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress)
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subTxt)
                            emailIntent.putExtra(Intent.EXTRA_TEXT, bodyTxt)

                            // on below line we are
                            // setting type of intent
                          //  intent.setType("message/rfc822")
                                .setType("text/plain");
                            // on the below line we are starting our activity to open email application.
                            context.startActivity(
                                Intent.createChooser(
                                    emailIntent,
                                    "Choose an Email client : "
                                )
                            )


                            //  loader = false
                        })
                    {
                        Text(text = "send mail")
                    }
                }
            }
        } // box
    } else {
       // viewModel.getResidentCompleteSearch2("", "", "")
       // val getAllUserData = viewModel.getUserData.observeAsState()

       // Log.e("getAllUserData", "resultList size: " + getAllUserData.value!![0].Provider_Name)
        viewModel.getResidentCompleteSearch("", "", "")

        if (isOneTime) {
            viewModel.residentCompleteSearchListResponse.forEach {

                if (it.MailID != null && !it.MailID.isEmpty()) {
                    resultList.addAll(listOf(it))
                }
            }
        }

        isOneTime = false
        // size of title
        var sizeCount = resultList.size
        var cc by remember { mutableStateOf(sizeCount) }

        //Log.e("resultList","ResultList: ${resultList.size}")
        LaunchedEffect(key1 = selectAll) {
            selectedItems =
                if (selectAll) selectedItems.plus(0..resultList.size) else selectedItems.minus(0..resultList.size)

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp, top = 150.dp)
                .verticalScroll(state = rememberScrollState(), enabled = true)
        )
        {
            Column {


                Row(
                    modifier = Modifier.toggleable(
                        value = selectAll,
                        onValueChange = {
                            selectAll = it
                            isEnabled = it
                            Log.e("All", "All:")
                            if (isEnabled) {
                                listCount.clear()
                                cc = 0
                                resultList.forEach {
                                    listCount.add(it.MailID.toString())
                                }

                            } else {
                                cc = sizeCount
                                listCount.clear()
                            }
                        },
                        role = Role.Checkbox
                    ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        text = "Available : $cc",
                        modifier = Modifier.padding(start = 15.dp)
                    )
                    Text(
                        text = "Selected : ${listCount.size}",
                        modifier = Modifier.padding(start = 55.dp)
                    )
                    Text(
                        text = "All",
                        modifier = Modifier.padding(start = 55.dp)
                    )
                    Checkbox(
                        checked = selectAll,
                        onCheckedChange = null,
                        modifier = Modifier
                            .padding(end = 0.dp, start = 0.dp)
                            .weight(8f)
                    )
                }


                Spacer(modifier = Modifier.padding(top = 8.dp))
                LazyVerticalGrid(columns = GridCells.Fixed(1), modifier = Modifier.height(350.dp))
                {
                    itemsIndexed(resultList) { index, item ->

                        EachRow2(
                            isEnabled = isEnabled,
                            item = item,
                            index = index,
                            selectedItem = selectedItems.contains(index),
                            onClick = {
                                selectedItems =
                                    if (selectedItems.contains(index)) {
                                        listCount.remove(item.MailID.toString())
                                        Log.e(
                                            "SIZE COUNT",
                                            "listCoSize:  ${item.MailID.toString()}"
                                        )
                                        // Log.e("SIZE COUNT","sizeCount:  ${sizeCount}")
                                        cc = sizeCount.minus(listCount.size)
                                        selectedItems.minus(index)
                                    } else {
                                        listCount.add(item.MailID.toString())
                                        Log.e("SIZE COUNT", "listCoSize:  ${listCount.size}")
                                        cc = sizeCount.minus(listCount.size)
                                        selectedItems.plus(index)

                                    }
                                /* listCount.add(item.MailID.toString())
                                 Log.e("SIZE COUNT","listCoSize:  ${listCount.size}")
                                 cc = sizeCount.minus(listCount.size)
                                 Log.e("SIZE COUNT"," $cc")*/


                            },

                            onEnableChange = { value ->
                                isEnabled = value
                            },
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(start = 15.dp, top = 3.dp)
                )
                {

                    OutlinedTextField(
                        value = subTxt,
                        onValueChange = { subTxt = it },
                        placeholder = { Text(text = "Subject") },
                        modifier = Modifier
                            .padding(bottom = 3.dp, end = 15.dp)
                            .fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = bodyTxt,
                        onValueChange = { bodyTxt = it },
                        placeholder = { Text(text = "Body") },
                        modifier = Modifier
                            .padding(bottom = 3.dp, end = 15.dp)
                            .fillMaxWidth()

                    )


                }
                Row(modifier = Modifier.padding(start = 15.dp, bottom = 5.dp))
                {
                    Column {
                        Button(
                            modifier = Modifier.padding(end = 3.dp),
                            onClick = {
                                // val intent = Intent(Intent.ACTION_GET_CONTENT)
                                // intent.setType("*/*")
                                // intent.putExtra("return-data", true);
                                //  context.startActivity(intent)
                                val intent = Intent()
                                intent.setType("*/*")

                                //intent.setType("text/plain");
                                intent.setAction(Intent.ACTION_GET_CONTENT)
                                intent.putExtra("return-data", true)


                                // startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
                                launcher.launch(intent)


                            }
                        )
                        {
                            Text(text = "Choose file")
                        }
                        Text(text = "filename: $fileName")
                    }
                    
                    Button(
                        onClick = {

                          //  val intent = Intent(Intent.ACTION_SEND)
                            val emailAddress = arrayOfNulls<String>(listCount.size)
                            // emailAddress[0] = "raju.elaboina@gmail.com"
                            for (i in 0 until listCount.size) {
                                emailAddress[i] = listCount[i]
                                Log.e("EEEEEE", listCount[i])
                            }
                            emailIntent.setType("text/plain")
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress)
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subTxt)
                            emailIntent.putExtra(Intent.EXTRA_TEXT, bodyTxt)

                            // on below line we are
                            // setting type of intent
                            emailIntent.setType("message/rfc822")

                            // on the below line we are starting our activity to open email application.
                            context.startActivity(
                                Intent.createChooser(
                                    emailIntent,
                                    "Choose an Email client : "
                                )
                            )


                            //  loader = false
                        })
                    {
                        Text(text = "send mail")
                    }
                }
            }
        } // box

    }
}


fun getResponseData(
    viewModel: ResidentViewModel,
    locationName: String,
    pgyName: String,
    specialityName: String,
) {
    viewModel.getResidentCompleteSearch(locationName , pgyName, specialityName)
    viewModel.residentCompleteSearchListResponse
    Log.e("List of Re","list Size: ${viewModel.residentCompleteSearchListResponse.size}")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EachRow2(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    item: ResidentCompleteSearchItem,
    index: Int,
    selectedItem: Boolean,
    onClick: () -> Unit,
    onEnableChange: (Boolean) -> Unit,
) {


    Box(
        modifier = modifier
            .padding(start = 0.dp, top = 0.dp)
            /*.background(Color.LightGray, RoundedCornerShape(4.dp))*/
            .combinedClickable(onLongClick = {
                onEnableChange(true)  //
            }, onClick = onClick)
    ) {
        Row(
            modifier = modifier
                .padding(start = 15.dp, top = 0.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // if (!item.MailID.isNullOrEmpty()) {

            Text(
                text = item.Provider_Name.trim(),
                modifier = Modifier
                    .weight(5f)
                    .padding(bottom = 0.dp),
                maxLines = 1
            )
            Checkbox(
                checked = selectedItem,
                onCheckedChange = { },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 0.dp)
            )

            // }

        }
    }


}

    

