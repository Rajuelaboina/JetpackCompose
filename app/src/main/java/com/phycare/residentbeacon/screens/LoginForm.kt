package com.phycare.residentbeacon.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phycare.residentbeacon.HomeActivity
import com.phycare.residentbeacon.LoginActivity
import com.phycare.residentbeacon.PreferencesManager
import com.phycare.residentbeacon.R
import com.phycare.residentbeacon.ui.theme.BeaconComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.http2.Http2Reader

@Composable
fun LoginForm() {
    var progress: Float by remember { mutableStateOf(0.75f) }
   // Surface {
       val scope = rememberCoroutineScope()
       val context = LocalContext.current.applicationContext
        val preferencesManager = remember { PreferencesManager(context) }
        val data = remember { mutableStateOf(preferencesManager.getData()) }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isShow by rememberSaveable { mutableStateOf(false) }
        var credentials by remember { mutableStateOf(Credentials()) }

            if (PreferencesManager(context).rememberGetData().remember){
                username =PreferencesManager(context).rememberGetData().login
            }else{
                username = ""
            }
          //  Kotlin, DevOps, Agile, GIT, Figma

    Log.e("IFIFIFI",">>>>>><<<< : "+username);
     Box(
        modifier =  Modifier.fillMaxWidth()

     ) {


         Column(
             modifier = Modifier
                 .fillMaxSize()
                 .padding(horizontal = 30.dp)
         ) {
             LoginField(
                 value = credentials.login,
                 onChange = { data -> credentials = credentials.copy(login = data) },
                 modifier = Modifier.fillMaxWidth(),
                 checkRemeb = credentials.remember

             )
             Spacer(modifier = Modifier.height(3.dp))
             PasswordField(
                 value = credentials.pwd,
                 onChange = { data -> credentials = credentials.copy(pwd = data) },
                 /*submit = {
                     if (!checkCredentials(credentials, context)) credentials = Credentials()
                 },*/
                 modifier = Modifier.fillMaxWidth()
             )
             Spacer(modifier = Modifier.height(6.dp))
             LabeledCheckbox(
                 label = "Remember Me",
                 modifier = Modifier.align(Alignment.Start),
                 onCheckChanged = {
                     credentials = credentials.copy(remember = !credentials.remember)
                 },
                 isChecked = credentials.remember
             )
             Spacer(modifier = Modifier.height(20.dp))
           Button(
                 onClick = {
                     isShow = true
                     scope.launch {
                         delay(1000)
                         if (!checkCredentials(credentials, context)) {
                             credentials = Credentials()
                         }
                         isShow = false
                     }
                    /* if (!checkCredentials(credentials, context)) {
                         isShow = false
                         credentials = Credentials()
                     }else{
                         isShow = true


                     }*/
                     //isShow = false
                 },
                 // enabled = credentials.isNotEmpty(),
                 shape = RoundedCornerShape(5.dp),
                 modifier = Modifier
                     .align(Alignment.CenterHorizontally)
                     .size(100.dp)
                     .padding(bottom = 10.dp)
             ) {
                 Text("Login")
             }
             if (isShow){
                 //if(credentials.isShowing){
                 Column(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalAlignment = Alignment.CenterHorizontally
                 ) {
                     CircularProgressIndicator()
                     Text(text = "loading..")
                 }
             }
         }
     }


   // }
}



fun checkCredentials(creds: Credentials, context: Context): Boolean {
    /*if (creds.isNotEmpty() *//*&& creds.login == "admin"*//*) {
        Log.e("Login user name","userName: "+creds.login)
        GlobalScope.launch {
        //    UserDatabase.getInstance(context).userDao().insertUser(User(0,creds.login,creds.pwd,creds.remember))
        }
        context.startActivity(Intent(context, HomeActivity::class.java))
       // (context as Activity).finish()
        return true
    } else {
        Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
        return false
    }*/
    if (creds.login.isEmpty()) {
        //Log.e("Login user name","userName: "+creds.login)
       /* GlobalScope.launch {
            //    UserDatabase.getInstance(context).userDao().insertUser(User(0,creds.login,creds.pwd,creds.remember))
        }*/
      //  context.startActivity(Intent(context, HomeActivity::class.java))
       // // (context as Activity).finish()
        Toast.makeText(context, "Username is required ", Toast.LENGTH_SHORT).show()
        return false
    }else if (creds.pwd.isEmpty()){
        Toast.makeText(context, "Password is required ", Toast.LENGTH_SHORT).show()
        return false
    }
    else {
        //Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
             creds.isShowing = true
        PreferencesManager(context).saveData(creds)
        PreferencesManager(context).rememberSaveData(creds)
        val intent = Intent(context, HomeActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
       context.startActivity(intent)

       val pref = PreferencesManager(context).getData()
        Log.e("SharedPref",pref.login)
        Log.e("SharedPref",pref.pwd)
        return true
    }
}

data class Credentials(
    var login: String = "",
    var pwd: String = "",
    var remember: Boolean = false,
    var isShowing: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return login.isNotEmpty() && pwd.isNotEmpty()
    }
}


@Composable
fun LabeledCheckbox(
    label: String,
    onCheckChanged: () -> Unit,
    isChecked: Boolean,
    modifier: Modifier
) {

    Row(
        Modifier
            .clickable(
                onClick = onCheckChanged
            )
            .padding(4.dp)
    ) {
        Checkbox(checked = isChecked, onCheckedChange = null)
        Spacer(Modifier.size(6.dp))
        Text(label)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Username",
    placeholder: String = "Enter your Username",
    checkRemeb: Boolean
) {

    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
  //  submit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "Enter your Password"
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Lock,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                // if (isPasswordVisible) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                if (isPasswordVisible) painterResource(id = R.drawable.ic_visibility_black_18dp) else painterResource(id = R.drawable.ic_visibility_off_black_18dp),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }


    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { //submit()
            }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}


@Preview(showBackground = true, device = "id:Nexus One", showSystemUi = true)
@Composable
fun LoginFormPreview() {
    BeaconComposeTheme {
        LoginForm()
    }
}

@Preview(showBackground = true, device = "id:Nexus One", showSystemUi = true)
@Composable
fun LoginFormPreviewDark() {
    BeaconComposeTheme(darkTheme = true) {
        LoginForm()
    }
}