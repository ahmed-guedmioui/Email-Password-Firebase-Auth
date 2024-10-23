package com.ag_apps.firebaseauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.ag_apps.firebaseauth.ui.theme.FirebaseAuthTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val email = "test_good@gmail.com"
    private val password = "T1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val authRepository = AuthRepository()

        setContent {
            FirebaseAuthTheme {
                var isLoggedIn by rememberSaveable {
                    mutableStateOf(authRepository.isLoggedIn())
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isLoggedIn) {

                        OutlinedButton(onClick = {
                            authRepository.logout()
                            isLoggedIn = false
                        }) {
                            Text(text = "Log Out")
                        }

                    } else {

                        OutlinedButton(onClick = {
                            lifecycleScope.launch {
                                isLoggedIn = authRepository.register(email, password)
                            }
                        }) {
                            Text(text = "Register")
                        }

                        Spacer(modifier = Modifier.height(50.dp))

                        OutlinedButton(onClick = {
                            lifecycleScope.launch {
                                isLoggedIn = authRepository.login(email, password)
                            }
                        }) {
                            Text(text = "Log In")
                        }
                    }
                }

            }
        }
    }
}


