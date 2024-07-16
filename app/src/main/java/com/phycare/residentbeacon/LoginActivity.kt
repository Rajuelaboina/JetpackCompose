package com.phycare.residentbeacon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phycare.residentbeacon.screens.LoginForm2
import com.phycare.residentbeacon.ui.theme.BeaconComposeTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        setContent {
            BeaconComposeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                  //  var username by remember { mutableStateOf("") }
                  //  var password by remember { mutableStateOf("") }
                  //  val checkedState = remember { mutableStateOf(false) }
                  //  val isError by rememberSaveable { mutableStateOf(false) }
                  //  var passwordVisibility: Boolean by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxSize().background(
                        color = Color.LightGray)
                    )
                    {

                        Column(modifier = Modifier.fillMaxSize().padding(top = 100.dp),
                            horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Column(modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .width(380.dp)
                                .height(370.dp),
                            )
                            {
                                Image(painterResource(id = R.drawable.resident_logo), contentDescription = null,
                                    modifier = Modifier.aspectRatio(6f / 1f))
                                Text(
                                    text = "Resident Beacon",
                                    color = Color.Blue,
                                    fontStyle = FontStyle.Normal,
                                     fontSize = 20.sp,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )

                                /*OutlinedTextField(
                                    value = username,
                                    onValueChange = {
                                        username = it
                                    },
                                    label = { Text(text = "User name")},
                                    isError = isError,
                                    shape = RoundedCornerShape(5.dp),
                                    placeholder = { Text(text = "User name")},
                                    textStyle = TextStyle(fontSize = 12.sp),
                                    trailingIcon = {

                                            Icon( Icons.Default.Person, contentDescription = null)


                                    },
                                    modifier = Modifier
                                        .padding(
                                            start = 10.dp, end = 10.dp, top = 10.dp, bottom = 0.dp
                                        )
                                        .fillMaxWidth()
                                        .align(Alignment.CenterHorizontally),
                                     keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                                    )
                                )

                                OutlinedTextField(
                                    value = password,
                                    onValueChange = {
                                        password = it
                                    },

                                    label = { Text(text = "Password")},
                                    isError = isError,
                                    shape = RoundedCornerShape(5.dp),
                                    placeholder = { Text(text = "Password")},
                                    textStyle = TextStyle(fontSize = 12.sp),
                                    trailingIcon = {
                                        IconButton(onClick = { passwordVisibility = !passwordVisibility })
                                        {
                                            Icon( painter = painterResource(id = if (passwordVisibility) R.drawable.ic_visibility_black_18dp else R.drawable.ic_visibility_off_black_18dp),
                                                contentDescription = null)

                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { submit() }
        ),
                                    modifier = Modifier
                                        .padding(
                                            start = 10.dp, end = 10.dp, top = 0.dp, bottom = 0.dp
                                        )
                                        .fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                                    )
                                )
                                Row {
                                    Checkbox(
                                        checked = checkedState.value, onCheckedChange = {checkedState.value = it},
                                        colors = CheckboxDefaults.colors(colorResource(id = R.color.btn_color)),
                                        modifier = Modifier
                                            .padding(start = 1.dp, top = 4.dp, end = 1.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                    Text(text = stringResource(id = R.string.remember_me),
                                        modifier = Modifier.align(Alignment.CenterVertically))
                                }
                                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                                        id = R.color.btn_color), contentColor = Color.White),
                                    onClick = { *//**//* }
                                )
                                {
                                    Text(text = "Login")
                                }*/

                                LoginForm2()
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    BeaconComposeTheme {
        Greeting3("Android")
    }
}
