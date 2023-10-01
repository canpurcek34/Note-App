package com.canpurcek.noteapp.userinterface.user

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.canpurcek.noteapp.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(){


    val modifier = Modifier


    Scaffold(
        modifier.fillMaxWidth(),
        topBar = {

        },
        content = {

            Column(modifier.fillMaxSize()) {


                Box(
                    modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp),
                    contentAlignment = Alignment.TopCenter
                ) {

                    Column(
                        modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        Text(
                            text = "Welcome!",
                            style = MaterialTheme.typography.displaySmall
                        )

                        Text(
                            text = "Let's continue the journey.",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }

                Spacer(
                    modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Column(
                    modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Row(
                        modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        OutlinedTextField(
                            label = {
                                Text(text = "Kullanıcı adı")
                            },
                            value = "username",
                            onValueChange = {

                            })

                    }

                    Spacer(
                        modifier
                            .fillMaxWidth()
                            .height(30.dp)
                    )


                    Row(
                        modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        OutlinedTextField(
                            label = {
                                Text(text = "Şifre")
                            },
                            value = "password",
                            onValueChange = {

                            })

                    }

                    Spacer(
                        modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            )

                    Row(
                        modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Text(text = "or")

                    }
                }

                Spacer(
                    modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )

                Row(
                    modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {

                    OutlinedIconButton(
                        onClick = {
                        
                    },
                        modifier.size(60.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription ="",
                            modifier.size(50.dp)
                        )
                    }

                }

                Spacer(
                    modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                    TextButton(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onBackground)
                            .align(alignment = Alignment.CenterHorizontally),
                        shape = ButtonDefaults.outlinedShape,
                        content = {
                            Text(text = "Log In", style = MaterialTheme.typography.labelMedium)

                        },
                        onClick = {

                    }
                    )
                }

        }
    )
}