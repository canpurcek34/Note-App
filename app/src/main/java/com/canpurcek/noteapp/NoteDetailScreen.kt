package com.canpurcek.noteapp

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.canpurcek.noteapp.entity.Notes
import com.canpurcek.noteapp.ui.theme.Chakra
import com.canpurcek.noteapp.viewmodel.NoteDetailScreenViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SimpleDateFormat")
@Composable
fun NoteDetailScreen(getNote: Notes, navController: NavController) {

    val tFNoteTitle = remember { mutableStateOf("") }
    val tFNote = remember { mutableStateOf("") }

    val colors = listOf<Color>(
        Color(0xfff28b82),
        Color(0xfffbbc04),
        Color(0xfffff475),
        Color(0xffccff90),
        Color(0xffa7ffeb),
        Color(0xffcbf0f8),
        Color(0xffaecbfa),
        Color(0xffd7aebf),
        Color(0xfffdcfe8),
        Color(0xffe6c9a8),
        Color(0xffe8eaed),
        Color(0xFF2C2C2C)
    )

    var currentlySelected by rememberSaveable(saver = colourSaver()) { mutableStateOf(colors[1]) }

    LaunchedEffect(key1 = true) {
        tFNoteTitle.value = getNote.note_title.toString()
        tFNote.value = getNote.note_desc.toString()
    }

    val viewModel: NoteDetailScreenViewModel = viewModel()


    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    var sheetShift by remember { mutableStateOf(false) }


        ModalBottomSheetLayout(
        sheetState = bottomState,
        content={
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = currentlySelected
                ) {
                    IconButton(
                        onClick = {

                            navController.popBackStack()

                        }) {

                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = ""
                        )
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(currentlySelected)
                        .padding(bottom = 35.dp)
                ) {
                    val lightBlue = Color(0xffd8e6ff)
                    val blue = Color(0xff76a9ff)
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = tFNoteTitle.value,
                        label = { Text(text = "Başlık") },
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontFamily = Chakra,
                            fontWeight = FontWeight.Medium
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            cursorColor = Color.Black,
                            disabledLabelColor = lightBlue,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        onValueChange = {

                            tFNoteTitle.value = it

                            val data1 = getNote.note_id
                            val data2 = tFNoteTitle.value
                            val data3 = tFNote.value
                            val sdf = SimpleDateFormat("EEE:HH:mm")
                            val date = sdf.format(Date())



                            if (data2.isEmpty()) {
                                return@TextField
                            } else {
                                viewModel.noteUpdate(data1, data2, data3, date)
                            }


                        },
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,

                        )
                    Spacer(modifier = Modifier.size(2.dp))
                    TextField(
                        modifier = Modifier.padding(bottom = 10.dp),
                        value = tFNote.value,
                        label = { Text(text = "Not") },
                        textStyle = TextStyle(
                            color = Color.DarkGray,
                            fontSize = 14.sp,
                            fontFamily = Chakra,
                            fontWeight = FontWeight.Normal
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            cursorColor = Color.Black,
                            disabledLabelColor = lightBlue,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        onValueChange = {
                            tFNote.value = it

                            val data1 = getNote.note_id
                            val data2 = tFNoteTitle.value
                            val data3 = tFNote.value
                            val sdf = SimpleDateFormat("EEE:HH:mm")
                            val date = sdf.format(Date())



                            if (data3.isEmpty()) {
                                return@TextField
                            } else {
                                viewModel.noteUpdate(data1, data2, data3, date)
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        singleLine = false,

                        )
                }

            },
            bottomBar = {
                androidx.compose.material3.BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    containerColor = BottomAppBarDefaults.containerColor,
                    content = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.multi_add),
                                contentDescription = "multi add", tint = Color.Black
                            )
                        }
                        IconButton(onClick = {

                          sheetShift = true

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.palette),
                                contentDescription = "note color palette", tint = Color.Black
                            )
                        }

                        val date = getNote.note_date

                        Text(text = "Düzenlenme zamanı:${date.toString()}")


                        IconButton(onClick = {

                            scope.launch { bottomState.show() }

                            sheetShift = false

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.menu),
                                contentDescription = "options menu", tint = Color.Black
                            )
                        }
                    }
                )
            }
        )
    },
        sheetContent = {

            if (!sheetShift) {

                Column(modifier = Modifier.fillMaxWidth()) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {

                            val id = getNote.note_id

                            viewModel.noteDelete(id)

                            navController.navigate("main_page")

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.delete),
                                contentDescription = "delete"
                            )
                        }
                        Text(text = "Sil")
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.copy),
                                contentDescription = "copy"
                            )
                        }
                        Text(text = "Kopya oluştur")
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.share),
                                contentDescription = "share"
                            )
                        }
                        Text(text = "Gönder")
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_alt),
                                contentDescription = "add_alt"
                            )
                        }
                        Text(text = "Ortak çalışan")
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.label),
                                contentDescription = "labels"
                            )
                        }
                        Text(text = "Etiketler")
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )
                }
            }else{


            }
        }
    )

    val backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        navController.popBackStack()

    }
}

fun colourSaver() = Saver<MutableState<Color>, String>(
    save = { state -> state.value.toHexString() },
    restore = { value -> mutableStateOf(value.toColor()) }
)

fun Color.toHexString(): String {
    return String.format(
        "#%02x%02x%02x%02x", (this.alpha * 255).toInt(),
        (this.red * 255).toInt(), (this.green * 255).toInt(), (this.blue * 255).toInt()
    )
}

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}



