package com.canpurcek.noteapp

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.canpurcek.noteapp.ui.theme.Chakra
import com.canpurcek.noteapp.ui.theme.DarkHomeBack
import com.canpurcek.noteapp.ui.theme.LightHomeBack
import com.canpurcek.noteapp.ui.theme.OpenSans
import com.canpurcek.noteapp.viewmodel.NoteDetailScreenViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*





@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SimpleDateFormat")
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


        ModalBottomSheetLayout(
        sheetState = bottomState,
        content={
        Scaffold(
            backgroundColor = MaterialTheme.colors.onPrimary,
            topBar = {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 0.dp,
                    backgroundColor = MaterialTheme.colors.onPrimary
                ) {
                    IconButton(
                        onClick = {

                            navController.popBackStack()

                        }) {

                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "",tint = MaterialTheme.colors.onSurface
                        )
                    }
                    lightColors(onSurface = LightHomeBack)
                    darkColors(onSurface = DarkHomeBack)
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
                    containerColor = MaterialTheme.colors.onPrimary,
                    content = {
                        darkColors(onSurface = LightHomeBack)
                        lightColors(onSurface = DarkHomeBack)

                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.multi_add),
                                contentDescription = "multi add", tint = MaterialTheme.colors.onSurface
                            )
                        }
                        IconButton(onClick = {

                            sheetShift = true

                            scope.launch { bottomState.show() }

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.palette),
                                contentDescription = "note color palette", tint = MaterialTheme.colors.onSurface
                            )
                        }

                        val date = getNote.note_date

                        Text(text = "Düzenlenme ${date.toString()}",
                            style = TextStyle(MaterialTheme.colors.onSurface))


                        IconButton(modifier = Modifier.padding(end = 5.dp),
                            onClick = {

                            sheetShift = false

                            scope.launch { bottomState.show() }

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.menu),
                                contentDescription = "options menu", tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                )
            }
        )
    },
        sheetContent = {

            if (sheetShift) {



            } else {

                Column(modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    darkColors(onSurface = LightHomeBack)
                    lightColors(onSurface = DarkHomeBack)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable{
                                val id = getNote.note_id

                                viewModel.noteDelete(id)

                                navController.navigate("main_page")
                        },
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

                        Text(text = "Sil",style = TextStyle(MaterialTheme.colors.onSurface))
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable{

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

                        Text(text = "Kopya oluştur",style = TextStyle(MaterialTheme.colors.onSurface))
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable{

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

                        Text(text = "Gönder",style = TextStyle(MaterialTheme.colors.onSurface))
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

                        Text(text = "Ortak çalışan",style = TextStyle(MaterialTheme.colors.onSurface))
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

                        Text(text = "Etiketler",style = TextStyle(MaterialTheme.colors.onSurface))
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )
                }
            }
        })

    val backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        navController.popBackStack()

    }
}






