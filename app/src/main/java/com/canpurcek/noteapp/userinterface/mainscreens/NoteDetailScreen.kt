package com.canpurcek.noteapp

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import com.canpurcek.noteapp.entity.Notes
import com.canpurcek.noteapp.ui.theme.*
import com.canpurcek.noteapp.userinterface.ColorPicker
import com.canpurcek.noteapp.userinterface.colourSaver
import com.canpurcek.noteapp.viewmodel.NoteDetailScreenViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SimpleDateFormat",
    "UnrememberedMutableState"
)
@Composable
fun NoteDetailScreen(getNote: Notes, navController: NavController) {

    val tFNoteTitle = remember { mutableStateOf("") }
    val tFNote = remember { mutableStateOf("") }


    LaunchedEffect(key1 = true) {
        tFNoteTitle.value = getNote.note_title.toString()
        tFNote.value = getNote.note_desc.toString()
    }

    val viewModel: NoteDetailScreenViewModel = viewModel()


    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()


    var sheetShift by remember { mutableStateOf(false) }

    val colors =listOf(
            Red,
            Orange,
            Yellow,
            Green,
            Turquoise,
            Blue,
            DarkBlue,
            Purple,
            Pink,
            Brown,
            Gray,
            DarkGray,
            LTheme,
            DTheme
        )



    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var currentlySelected by mutableStateOf(MaterialTheme.colors.surface)





    ModalBottomSheetLayout(
        sheetState = bottomState,
        content={
        Scaffold(
            backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
            snackbarHost = { hostState ->
                hostState.currentSnackbarData?.let { DeleteSnack(it.message) }
            },
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
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
                                tint = MaterialTheme.colors.onSurface,
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
                    elevation = 0.dp,
                    backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    IconButton(
                        onClick = {

                            val data1 = getNote.note_id
                            val data2 = tFNoteTitle.value
                            val data3 = tFNote.value
                            val sdf = SimpleDateFormat("EEE:HH:mm")
                            val date = sdf.format(Date())
                            val color = currentlySelected



                            if (data2.isEmpty()) {

                            } else {
                                viewModel.noteUpdate(data1, data2, data3, date, color.toString())
                            }
                            navController.popBackStack()

                        }) {

                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "",
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                        )
                    }

                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .padding(bottom = 35.dp)
                ) {
                    val lightBlue = Color(0xffd8e6ff)
                    val blue = Color(0xff76a9ff)
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = tFNoteTitle.value,
                        label = { Text(text = "Başlık",style = TextStyle(MaterialTheme.colors.onSurface)) },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = OpenSans,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.W500
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

                            val data1 = getNote.note_id
                            val data2 = tFNoteTitle.value
                            val data3 = tFNote.value
                            val sdf = SimpleDateFormat("EEE:HH:mm")
                            val date = sdf.format(Date())
                            val color = currentlySelected



                            if (data2.isEmpty()) {
                                return@TextField
                            } else {
                                viewModel.noteUpdate(data1, data2, data3, date, color.toString())
                            }


                        },
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,

                        )
                    Spacer(modifier = Modifier.size(2.dp))
                    TextField(
                        modifier = Modifier.padding(bottom = 10.dp),
                        value = tFNote.value,
                        label = { Text(text = "Not",style = TextStyle(MaterialTheme.colors.onSurface)) },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = OpenSans,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.W400
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

                            val data1 = getNote.note_id
                            val data2 = tFNoteTitle.value
                            val data3 = tFNote.value
                            val sdf = SimpleDateFormat("EEE:HH:mm")
                            val date = sdf.format(Date())
                            val color = Color.White



                            if (data3.isEmpty()) {
                                return@TextField
                            } else {
                                viewModel.noteUpdate(data1, data2, data3, date, color.toString())
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
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                    contentColor = androidx.compose.material3.MaterialTheme.colorScheme.surface
                ) {

                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.multi_add),
                            contentDescription = "multi add", tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                        )
                    }

                    ColorPicker(
                        colors = colors,
                        onColorSelected = {

                        currentlySelected=it

                    }, modifier = Modifier.background(androidx.compose.material3.MaterialTheme.colorScheme.onBackground))


                    val date = getNote.note_date

                    Text(
                        text = "Düzenlenme ${date.toString()}",
                        style = TextStyle(androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
                    )


                    IconButton(modifier = Modifier.padding(end = 5.dp),
                        onClick = {

                            sheetShift = false

                            scope.launch { bottomState.show() }

                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.menu),
                            contentDescription = "options menu",
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            },

        )
    },
        sheetContent = {

                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .height(200.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {

                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("Not silindi!")
                                    }



                                    val id = getNote.note_id

                                    viewModel.noteDelete(id)

                                    navController.navigate("main_page")
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "delete"
                        )

                        Spacer(
                            modifier = Modifier
                                .width(5.dp)
                        )

                        Text(text = "Sil", style = TextStyle(MaterialTheme.colors.onSurface))
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.copy),
                            contentDescription = "copy"
                        )
                        Spacer(
                            modifier = Modifier
                                .width(5.dp)
                        )

                        Text(
                            text = "Kopya oluştur",
                            style = TextStyle(MaterialTheme.colors.onSurface)
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.share),
                            contentDescription = "share"
                        )
                        Spacer(
                            modifier = Modifier
                                .width(5.dp)
                        )

                        Text(text = "Gönder", style = TextStyle(MaterialTheme.colors.onSurface))
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.add_alt),
                            contentDescription = "add_alt"
                        )
                        Spacer(
                            modifier = Modifier
                                .width(5.dp)
                        )

                        Text(
                            text = "Ortak çalışan",
                            style = TextStyle(MaterialTheme.colors.onSurface)
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.label),
                            contentDescription = "labels"
                        )
                        Spacer(
                            modifier = Modifier
                                .width(5.dp)
                        )

                        Text(text = "Etiketler", style = TextStyle(MaterialTheme.colors.onSurface))
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )
                    DeleteSnack()
                }
            }
        )

    val backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {

        val data1 = getNote.note_id
        val data2 = tFNoteTitle.value
        val data3 = tFNote.value
        val sdf = SimpleDateFormat("EEE:HH:mm")
        val date = sdf.format(Date())
        val color = currentlySelected



        if (data2.isEmpty()) {

        } else {
            viewModel.noteUpdate(data1, data2, data3, date, color.toString())
        }

        colourSaver()
        navController.popBackStack()


    }
}


@Composable
private fun DeleteSnack(
    text: String = "Not silindi!"
) {
    Snackbar(
        content = { Text(text = text, color = Color.White) }
    )
}





