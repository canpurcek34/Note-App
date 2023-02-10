package com.canpurcek.noteapp.userinterface.mainscreens

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.canpurcek.noteapp.R
import com.canpurcek.noteapp.ui.theme.*
import com.canpurcek.noteapp.viewmodel.AddNoteScreenViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SimpleDateFormat",
    "UnrememberedMutableState"
)
@Composable
fun AddNoteScreen(NavController: NavHostController) {


    val tFNoteTitle = remember { mutableStateOf<String>("") }
    val tFNote = remember { mutableStateOf<String>("") }


    val viewModel: AddNoteScreenViewModel = viewModel()

    val navController = NavController


    var currentlySelected by  mutableStateOf(MaterialTheme.colors.surface)

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
                            navController.navigate("main_page")
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

                        androidx.compose.material.Text(
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

                        val title = tFNoteTitle.value
                        val desc = tFNote.value
                        val sdf = SimpleDateFormat("EEE:HH:mm")
                        val date = sdf.format(Date())
                        val color = currentlySelected

                        if (title.isEmpty()) {
                            NavController.navigate("main_page")
                        } else if (desc.isEmpty()) {
                            NavController.navigate("main_page")
                        } else {
                            viewModel.noteAdd(title, desc, date, color.toString())

                        }



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
            lightColors(onSurface = MaterialTheme.colors.surface)
            darkColors(onSurface = MaterialTheme.colors.surface)

            Column(modifier = Modifier.fillMaxSize()) {
                val lightBlue = Color(0xffd8e6ff)
                val blue = Color(0xff76a9ff)
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = tFNoteTitle.value,
                    label = { Text(text = "Başlık",style = TextStyle(MaterialTheme.colors.onSurface)) },
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = Chakra,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = MaterialTheme.colors.onSurface,
                        disabledLabelColor = lightBlue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        textColor = MaterialTheme.colors.onSurface
                    ),
                    onValueChange = {

                        tFNoteTitle.value = it

                    },
                    shape = RoundedCornerShape(8.dp),
                )
                Spacer(modifier = Modifier.size(2.dp))
                TextField(
                    modifier = Modifier,
                    value = tFNote.value,
                    label = { Text(text = "Not", style = TextStyle(MaterialTheme.colors.onSurface)) },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = Chakra,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = MaterialTheme.colors.onSurface,
                        disabledLabelColor = lightBlue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        textColor = MaterialTheme.colors.onSurface
                    ),
                    onValueChange = {
                        tFNote.value = it
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
                backgroundColor = MaterialTheme.colors.onPrimary,
                content = {
                    darkColors(onSurface = Color.LightGray)
                    lightColors(onSurface = Color.White)
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.multi_add),
                            contentDescription = "multi add",tint = MaterialTheme.colors.onSurface
                        )
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.palette),
                            contentDescription = "note color palette",tint = MaterialTheme.colors.onSurface
                        )
                    }
                    val sdf = SimpleDateFormat("EEE:HH:mm")
                    val date = sdf.format(Date())

                    Text(modifier = Modifier,
                        text = "Düzenlenme ${date}",
                        style = TextStyle(MaterialTheme.colors.onSurface))

                }

            )
        }
    )



    val backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {

        val title = tFNoteTitle.value
        val desc = tFNote.value
        val sdf = SimpleDateFormat("EEE:HH:mm")
        val date = sdf.format(Date())
        val color = currentlySelected

        if ((title + desc).isNotEmpty()) {

            viewModel.noteAdd(title, desc, date, color.toString())

            Log.e("KAYIT", "${title}-${desc}")
        }
        NavController.popBackStack()


    }
}





