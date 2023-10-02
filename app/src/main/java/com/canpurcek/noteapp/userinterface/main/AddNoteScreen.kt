package com.canpurcek.noteapp.userinterface.main

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.canpurcek.noteapp.R
import com.canpurcek.noteapp.viewmodel.AddNoteScreenViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SimpleDateFormat",
    "UnrememberedMutableState"
)
@Composable
fun AddNoteScreen(NavController: NavHostController) {


    val id = remember{ mutableStateOf(0) }
    val title = remember { mutableStateOf("") }
    val note = remember { mutableStateOf("") }
    val sdf = SimpleDateFormat("EEE:HH:mm")
    val date = Date()
    val editDate= remember { mutableStateOf(sdf.format(date)) }

    val viewModel: AddNoteScreenViewModel = viewModel()

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
        drawerContent = {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .padding(start = 8.dp, top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(24.dp))


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                            NavController.navigate("main_page")
                        },
                    backgroundColor = if (isSystemInDarkTheme()) MaterialTheme.colors.surface else Color.LightGray,
                    elevation = 0.dp,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "Icon",
                            modifier = Modifier,
                            tint = MaterialTheme.colors.surface,
                        )

                        Text(
                            modifier = Modifier
                                .padding(start = 24.dp),
                            text = "Ana Sayfa",
                        )
                    }
                }
            }
        },
        drawerGesturesEnabled = true,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background
            ) {
                IconButton(

                    onClick = {


                        val newTitle = title.value
                        val newNote = note.value
                        val newDate = editDate.value

                        viewModel.insert(newTitle,newNote,newDate)

                        NavController.navigate("main_page")

                    }) {

                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "",tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                    )
                }

            }

        },
        content = {

            Column(modifier = Modifier.fillMaxSize()) {

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title.value,
                    label = {
                        androidx.compose.material3.Text(
                            text = "Başlık",
                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                        )
                    },
                    textStyle = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        textColor = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                    ),
                    onValueChange = {

                        title.value = it

                    },
                    shape = RoundedCornerShape(8.dp),
                )
                Spacer(modifier = Modifier.size(2.dp))

                TextField(
                    modifier = Modifier.fillMaxSize(),
                    value = note.value,
                    label = {
                        androidx.compose.material3.Text(
                            text = "Not",
                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                        )
                    },
                    textStyle = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        textColor = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                    ),
                    onValueChange = {
                        note.value = it
                    },
                    shape = RoundedCornerShape(8.dp),

                    )
            }

        },
        bottomBar = {

            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
                content = {

                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.multi_add),
                            contentDescription = "multi add",tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                        )
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.palette),
                            contentDescription = "note color palette",tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Text(modifier = Modifier,
                        text = "Düzenlenme $date",
                        style = TextStyle(androidx.compose.material3.MaterialTheme.colorScheme.onBackground))

                }

            )
        }
    )



    val backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {

        val newTitle = title.value
        val newNote = note.value
        val newDate = editDate.value

        viewModel.insert(newTitle,newNote,newDate)
    }

}





