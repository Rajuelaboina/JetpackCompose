package com.phycare.residentbeacon.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.phycare.residentbeacon.HomeActivity
import com.phycare.residentbeacon.PreferencesManager
import com.phycare.residentbeacon.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun LoginForm2() {
    var progress: Float by remember { mutableStateOf(0.75f) }
   // Surface {
        val scope = rememberCoroutineScope()
        val context = LocalContext.current.applicationContext
        val preferencesManager = remember { PreferencesManager(context) }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isChecked  = remember { mutableStateOf(false) }
        var isShow by rememberSaveable { mutableStateOf(false) }
        var isValid by remember {mutableStateOf(0)}
        var isUnameError by remember {mutableStateOf(true)}
        var isPwdError by remember {mutableStateOf(true)}
        var credentials by remember { mutableStateOf(Credentials()) }

            // -------- One time Login --------------------
             if (PreferencesManager(context).getData().login.isNotEmpty()){
                 PreferencesManager(context).rememberSaveData(Credentials(username,password,isChecked.value))
                 val intent = Intent(context, HomeActivity::class.java)
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                 context.startActivity(intent)
             }
             //-------- remember password ------------------
            if (PreferencesManager(context).rememberGetData().remember){
                username = PreferencesManager(context).rememberGetData().login
                password = PreferencesManager(context).rememberGetData().pwd
                isChecked.value = PreferencesManager(context).rememberGetData().remember
            }
          //  Kotlin, DevOps, Agile, GIT, Figma


     Box(modifier =  Modifier.fillMaxWidth())
     {
         Column(  modifier = Modifier
             .fillMaxSize()
             .padding(horizontal = 30.dp))
         {
             val focusManager = LocalFocusManager.current
             val leadingIcon = @Composable {
                 Icon(
                     Icons.Default.Person,
                     contentDescription = "",
                     tint = MaterialTheme.colorScheme.primary
                 )
             }

             OutlinedTextField(
                 value = username,
                 onValueChange = {
                     username = it
                     isUnameError = true},
                 modifier = Modifier,
                 leadingIcon = leadingIcon,
                 keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                 keyboardActions = KeyboardActions(
                     onNext = { focusManager.moveFocus(FocusDirection.Down) }
                 ),
                 placeholder = { Text("enter user name") },
                 label = { Text("Username") },
                 singleLine = true,
                 visualTransformation = VisualTransformation.None,

             )
             var str =""
             if (isUnameError){
                 str = ""
             }else{
                 str = "user name is required"
             }
             Text(text = str, color = Color.Red, style = MaterialTheme.typography.titleSmall)
             var isPasswordVisible by remember { mutableStateOf(false) }
             val leadingIcon2 = @Composable {
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
                 value = password,
                 onValueChange = {
                     password = it
                     isPwdError = true},
                 modifier = Modifier,
                 leadingIcon = leadingIcon2,
                 trailingIcon = trailingIcon,
                 keyboardOptions = KeyboardOptions(
                     imeAction = ImeAction.Done,
                     keyboardType = KeyboardType.Password
                 ),
                 keyboardActions = KeyboardActions(
                     onDone = { //submit()
                     }
                 ),
                 placeholder = { Text("enter password") },
                 label = { Text("Password") },
                 singleLine = true,
                 visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
             )
             var pwdstr =""
             if (isPwdError){
                 pwdstr = ""
             }else{
                 pwdstr = "password is required"
             }
             Text(text = pwdstr, color = Color.Red, style = MaterialTheme.typography.titleSmall)
             //Spacer(Modifier.size(6.dp))
             Row(verticalAlignment = Alignment.CenterVertically,
             ) {
                 Checkbox(checked = isChecked.value, onCheckedChange = {
                     //credentials = credentials.copy(remember = !credentials.remember)
                    isChecked.value = it
                     PreferencesManager(context).rememberSaveData(Credentials(username,password,it))
                 }
                 )
                 Text("Remember Me")
             }
            // Spacer(modifier = Modifier.height(0.dp))
           Button(
                 onClick = {
                     isShow = true
                     scope.launch {
                         delay(1000)
                         isValid = isValidCredentials(username,password,context)

                         if (isValid == -1) {
                           //Log.e("Succcess","Sucess    $username,$password")
                             PreferencesManager(context).saveData(Credentials(username,password,isChecked.value))
                             PreferencesManager(context).rememberSaveData(Credentials(username,password,isChecked.value))
                             val intent = Intent(context, HomeActivity::class.java)
                             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                             context.startActivity(intent)
                         //    Log.e("Succcess","else >>>>>>>>>>>>>>"+PreferencesManager(context).getData().login);
                         }else if (isValid == 1)
                         {
                             isUnameError = false
                             Log.e("Succcess","else >>>>>>>>>>>>>>")
                         }else if (isValid == 2)
                         {
                             isPwdError = false
                             Log.e("Succcess","else >>>>>>>>>>>>>>")
                         }
                        isShow = false
                     }
                 },
                 // enabled = credentials.isNotEmpty(),
                 shape = RoundedCornerShape(25.dp),
                 modifier = Modifier.height(ButtonDefaults.MinHeight)
                     .align(Alignment.CenterHorizontally)
              ) {
               Row(verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    var str ="Login"
                   if (isShow){
                       str = "Loading"
                       CircularProgressIndicator(color = Color.White,
                           modifier = Modifier.size(ButtonDefaults.IconSize),
                           strokeWidth = 2.dp)
                   }
                   Text(str, textAlign = TextAlign.Center,style = MaterialTheme.typography.titleMedium)
               }
             }
         }
     }
}

fun isValidCredentials(username: String, password: String, context: Context): Int {
    if (username.isEmpty()){
        Toast.makeText(context, "Username is required ", Toast.LENGTH_SHORT).show()
        return 1
    }else if (password.isEmpty()){
        Toast.makeText(context, "Password is required ", Toast.LENGTH_SHORT).show()
        return 2
    }else{
        return -1
    }
}



