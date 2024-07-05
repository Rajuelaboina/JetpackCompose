package com.phycare.residentbeacon

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.phycare.residentbeacon.ui.theme.BeaconComposeTheme
import java.io.File


class AddUserActivity : ComponentActivity() {
    private val viewModel: ResidentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            BeaconComposeTheme {
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()) {
                    val uri = remember { mutableStateOf<Uri?>(null) }
                    AddUserView(
                        directory = File(cacheDir, "images"),
                        uri = uri.value,
                        onSetUri = {
                            uri.value = it
                        },
                        viewModel = viewModel
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddUserView(
    uri: Uri? = null, //target url to preview
    directory: File? = null, // stored directory
    onSetUri: (Uri) -> Unit = {}, // selected / taken uri
    viewModel: ResidentViewModel,
) {
    viewModel.getAllStates()
    viewModel.getAllPGy()
    viewModel.getAllSpeciality()

    val context = LocalContext.current
    val tempUri = remember { mutableStateOf<Uri?>(null) }
    val authority = stringResource(id = R.string.fileprovider)
    fun getTempUri(): Uri? {
        directory?.let {
            it.mkdirs()
            val file = File.createTempFile(
                "image_" + System.currentTimeMillis().toString(),
                ".jpg",
                it
            )

            return FileProvider.getUriForFile(
                context,
                authority,
                file
            )
        }
        return null
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                onSetUri.invoke(it)
            }
        }
    )

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { _ ->
            tempUri.value?.let {
                onSetUri.invoke(it)
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted, proceed to step 2
            val tmpUri = getTempUri()
            tempUri.value = tmpUri
            tempUri.value?.let {
                takePhotoLauncher.launch(it)
            }

        } else {
            // Permission is denied, handle it accordingly
        }
    }

    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        MyModalBottomSheet(
            onDismiss = {
                showBottomSheet = false
            },
            onTakePhotoClick = {
                showBottomSheet = false

                val permission = Manifest.permission.CAMERA
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is already granted, proceed to step 2
                    val tmpUri = getTempUri()
                    tempUri.value = tmpUri
                    tempUri.value?.let {
                        takePhotoLauncher.launch(it)
                    }

                } else {
                    // Permission is not granted, request it
                    cameraPermissionLauncher.launch(permission)
                }
            },
            onPhotoGalleryClick = {
                showBottomSheet = false

                //Android is 11 (R) or above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Environment.isExternalStorageManager()
                }
                imagePicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
        )
    }
    // view start
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                /*.background(color = Color.LightGray)*/,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //preview selfie
            //  uri?.let {
            Box(modifier = Modifier.fillMaxWidth()) {
                /*Image(
                    painterResource(id = R.drawable.doctor),
                    contentDescription = "AdduserProfile Image",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(120.dp)
                        .padding(top = 13.dp),
                )*/
                AsyncImage(
                    model = uri,
                    contentDescription = "Resident user Image",
                    placeholder = painterResource(id = R.drawable.doctor),
                    error = painterResource(id = R.drawable.doctor),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)
                        .align(alignment = Alignment.Center)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )

                IconButton(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 55.dp, top = 10.dp),
                    onClick = {
                        showBottomSheet = true

                    }
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_photo_camera_24),
                        contentDescription = null
                    )
                }

            }
            //   }

        }// 1st child column close

        Column(
            modifier = Modifier
                .height(650.dp)
                .verticalScroll(state = rememberScrollState(), enabled = true)
                .fillMaxWidth(),verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            var residentName by remember { mutableStateOf("") }
            val isError by rememberSaveable { mutableStateOf(false) }
            var locationName by remember { mutableStateOf("") }
            var pgyName by remember { mutableStateOf("") }
            var expanded by remember { mutableStateOf(false) }
            var selectedItemIndex by remember { mutableIntStateOf(0) }
            var expandedPgy by remember { mutableStateOf(false) }
            var selectedItemIndexPgy by remember { mutableIntStateOf(0) }


            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = "Provider name") },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )

              ///---------------------
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
            )
            {
                OutlinedTextField(
                    value = viewModel.stateListResponse[selectedItemIndex].Location,
                    onValueChange = { locationName = viewModel.stateListResponse[selectedItemIndex].Location },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .fillMaxSize(),
                    shape = RoundedCornerShape(5.dp),
                    label = { Text("Location") },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),

                )

                ExposedDropdownMenu(expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    viewModel.stateListResponse.forEachIndexed { index, item ->
                        //  var userCity = item.Location
                        DropdownMenuItem(text = {
                            Text(
                                text = item.Location,
                                fontWeight = if (index == selectedItemIndex) FontWeight.Bold else null
                            )
                           // itemsp = item.Location
                        }, onClick = {
                            selectedItemIndex = index
                            expanded = false
                            locationName = if (item.Location == "All") {
                                ""
                            } else {
                                //providerName = item.toString()
                                //showWebView = true
                                item.Location
                            }

                            // Log.e("locationName<>>>>>>>>>", "locationName :" + locationName)
                            //showWebView = true
                        })


                    }
                }

            }
            // -----------------------------
            ExposedDropdownMenuBox(
                expanded = expandedPgy,
                onExpandedChange = { expandedPgy = !expandedPgy },
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 5.dp, bottom = 1.dp
                    )
            ) {
                OutlinedTextField(
                    value = viewModel.pgyListResponse[selectedItemIndexPgy].PGY, onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPgy) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .fillMaxSize(),
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
                    viewModel.pgyListResponse.forEachIndexed { index, item ->
                        // var userpgy = item.PGY
                        DropdownMenuItem(text = {
                            Text(
                                text = item.PGY,
                                fontWeight = if (index == selectedItemIndexPgy) FontWeight.Bold else null
                            )

                        }, onClick = {
                            selectedItemIndexPgy = index
                            expandedPgy = false
                            pgyName = if (item.PGY == "All") {
                                ""
                            } else {
                                item.PGY
                                // showWebView = true
                            }

                            //   Log.e("ListSize<>>>>>>>>>", "Size of :" + pgyName)

                        })

                    }
                }

            }
            //----------------------------------------------
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.classof)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.residency)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.startyear)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.education)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.graduate_school)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.under_graduate_college)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.medical_school)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.internship)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.track)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.major)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.fellowship)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.future_plan)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.hometown)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.instagram_id)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.twitter_id)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.mail_id)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = stringResource(id = R.string.phone_no)) },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = "FAX No") },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = "Address") },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                value = residentName,
                onValueChange = {
                    residentName = it
                },
                label = { Text(text = "MIS") },
                isError = isError,
                shape = RoundedCornerShape(5.dp),
                // placeholder = { Text(text = "Enter Username")},
                modifier = Modifier
                    .padding(
                        start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            )


        }// column

        // last column ------------
        Column(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Button(modifier = Modifier
                    .weight(1f)
                    .padding(end = 5.dp),onClick = {  }) {
                    Text(text = "Back")
                }
                Button(modifier = Modifier.weight(1f),onClick = {  }) {
                    Text(text = "Create User")
                }
            }
        }

    } // main column
}


/*
Model: This layer is responsible for the abstraction of the data sources. Model and ViewModel work together to get and save the data.
View: The purpose of this layer is to inform the ViewModel about the user’s action. This layer observes the ViewModel and does not contain any kind of application logic.
ViewModel: It exposes those data streams which are relevant to the View. Moreover, it serves as a link between the Model and the View.
*/

