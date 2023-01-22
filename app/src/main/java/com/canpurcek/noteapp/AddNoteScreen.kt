package com.canpurcek.noteapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
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
import com.canpurcek.noteapp.entity.Notes
import com.canpurcek.noteapp.ui.theme.Chakra
import com.canpurcek.noteapp.ui.theme.DarkHomeBack
import com.canpurcek.noteapp.ui.theme.LightHomeBack
import com.canpurcek.noteapp.viewmodel.AddNoteScreenViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SimpleDateFormat")
@Composable
fun AddNoteScreen(NavController: NavHostController) {


    val tFNoteTitle = remember { mutableStateOf<String>("") }
    val tFNote = remember { mutableStateOf<String>("") }


    val viewModel: AddNoteScreenViewModel = viewModel()

    val navController = NavController


    Scaffold(
        backgroundColor = MaterialTheme.colors.onPrimary,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.onPrimary
            ) {
                IconButton(

                    onClick = {

                        val title = tFNoteTitle.value
                        val desc = tFNote.value
                        val sdf = SimpleDateFormat("EEE:HH:mm")
                        val date = sdf.format(Date())

                        if (title.isEmpty()) {
                            return@IconButton
                        } else if (desc.isEmpty()) {
                            return@IconButton
                        } else {
                            viewModel.noteAdd(title, desc, date)

                            Log.e("KAYIT", "${title}-${desc}")
                        }



                        NavController.navigate("main_page")

                    }) {

                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "",tint = MaterialTheme.colors.onSurface
                    )
                }

            }

        },
        content = {
            lightColors(onSurface = MaterialTheme.colors.onPrimary)
            darkColors(onSurface = MaterialTheme.colors.onPrimary)

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

        if ((title + desc).isNotEmpty()) {

            viewModel.noteAdd(title, desc, date)

            Log.e("KAYIT", "${title}-${desc}")
        }
        NavController.popBackStack()


    }
}





