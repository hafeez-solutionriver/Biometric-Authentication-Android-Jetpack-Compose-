package com.example.rdbiometricprompt

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.rdbiometricprompt.ui.theme.RDBiometricPromptTheme

class MainActivity : FragmentActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isAuthenticated = remember { mutableStateOf(false) }
            val context = LocalContext.current
            RDBiometricPromptTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {


                    val con = Biometric.status(LocalContext.current)
                    if(con && !isAuthenticated.value)
                    {
                        Biometric.authenticate(
                            this@MainActivity,
                            title = "Authentication Required",
                            negativeText = "Password",
                            onSuccess = {
                                runOnUiThread {
                                    Toast.makeText(
                                        context,
                                        "Authenticated successfully",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    isAuthenticated.value=true
                                }
                            },
                            onError = { _, _ ->
                               /*
                               On Clicking on Password
                                */

                            },
                            onFailed = {
                                runOnUiThread {
                                    Toast.makeText(
                                        context,
                                        "Authentication failed",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    Log.d("Fail ->","")
                                }
                            }
                    )
                }

                    if(isAuthenticated.value)
                    {
                        Greeting(name = "Welcome To The Screen after authentication")
                    }


                    }

                }
            }
        }
    }

@Composable
fun Greeting(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // Adjust the padding as needed
            .wrapContentHeight(),
        backgroundColor = Color.White,
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(16.dp), // Adjust the padding inside the card
            text = "Welcome to Homepage",
            style = TextStyle(fontSize = 16.sp) // Customize the text style
        )
    }
}