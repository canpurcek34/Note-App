package com.canpurcek.noteapp.userinterface.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.canpurcek.noteapp.R
import com.canpurcek.noteapp.retrofit.JSON.Notebook
import com.canpurcek.noteapp.ui.theme.*
import com.canpurcek.noteapp.userinterface.colourSaver
import com.canpurcek.noteapp.viewmodel.NoteDetailScreenViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SimpleDateFormat",
    "UnrememberedMutableState"
)
@Composable
fun NoteDetailScreen(obj: Notebook, navController: NavController) {

    val viewModel: NoteDetailScreenViewModel = viewModel()
    val notes = viewModel.notebook.observeAsState(listOf())

    val localFocusManager = LocalFocusManager.current

    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val id = remember{ mutableStateOf(0) }
    val title = remember { mutableStateOf("") }
    val note = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }

    LaunchedEffect(key1 =true){
         id.value = obj.id
         title.value= obj.title
         note.value = obj.note
         date.value = obj.date
    }


    var sheetShift by remember { mutableStateOf(false) }


    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val currentlySelected by mutableStateOf(MaterialTheme.colors.surface)

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

                            val newTitle = title.value
                            val newNote = note.value
                            val newDate = date.value

                            viewModel.update(obj.id,newTitle,newNote,newDate)


                            navController.navigate("main_page")

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
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = title.value,
                        label = {
                            Text(
                                text = "Başlık",
                                style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                            ) },
                        textStyle = androidx.compose.material3.MaterialTheme.typography.titleSmall,
                        colors =
                        TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            cursorColor = MaterialTheme.colors.onSurface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            textColor = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                        ),
                        onValueChange ={
                                       title.value=it
                        },
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        )

                    Spacer(modifier = Modifier.size(2.dp))

                    TextField(
                        modifier = Modifier.padding(bottom = 10.dp),
                        value = note.value,
                        label = {
                            Text(
                            text = "Not",
                            style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                            ) },
                        textStyle = androidx.compose.material3.MaterialTheme.typography.titleSmall,
                        colors =
                        TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            cursorColor = MaterialTheme.colors.onSurface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            textColor = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                        ),
                        onValueChange = {
                            note.value=it
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
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {

                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.multi_add),
                            contentDescription = "multi add", tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                        )
                    }

                    val dateEdit = date

                    Text(
                        text = "Düzenlenme $dateEdit",
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

                                    navController.navigate("main_page")
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier= Modifier,
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
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Not silindi!",
                                        duration = SnackbarDuration.Short
                                    )
                                }
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

                }
            }
        )

    val backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {


        viewModel.update(obj.id,obj.title,obj.note,obj.date)
        navController.navigate("main_page")

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





