package com.canpurcek.noteapp.userinterface.notegrid

import android.app.Application
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.canpurcek.noteapp.entity.Notes
import com.canpurcek.noteapp.ui.theme.OpenSans
import com.canpurcek.noteapp.viewmodel.HomeScreenViewModel
import com.canpurcek.noteapp.viewmodelfactory.HomeScreenViewModelFactory
import com.google.gson.Gson

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridView(
    modifier: Modifier,
    navController: NavController

    ){
    val context = LocalContext.current

    val viewModel: HomeScreenViewModel = viewModel(
        factory = HomeScreenViewModelFactory(
            context.applicationContext as Application

        )
    )
    val allNotes = viewModel.notes.observeAsState(listOf())

    val itemCount = allNotes.value!!.count()
    val frameCount = 2



    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp),
        userScrollEnabled = true,
        columns = StaggeredGridCells.Fixed(frameCount),
        content = {
            items(itemCount) {


                ElevatedCard(
                    colors = CardDefaults.cardColors(androidx.compose.material3.MaterialTheme.colorScheme.tertiaryContainer),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .padding(3.dp)
                        .sizeIn(maxHeight = 250.dp)
                        .combinedClickable(onClick = {

                            val note = allNotes.value!![it]

                            val noteJson =
                                Uri.encode(Gson().toJson(note))

                            navController.navigate("note_details_page/${noteJson}")

                        }
                        ),
                    content = {

                        Column(
                            modifier = Modifier.padding(5.dp)
                        ) {
                            val note: Notes = allNotes.value[it]

                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(alignment = Alignment.CenterVertically)
                                        .padding(
                                            start = 8.dp,
                                            end = 8.dp,
                                            top = 4.dp
                                        )
                                        .weight(1f)
                                        .wrapContentWidth(Alignment.Start),
                                    text = note.note_title,
                                    overflow = TextOverflow.Ellipsis,
                                    style = androidx.compose.material3.MaterialTheme.typography.labelLarge,
                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp),
                            )


                            Text(
                                modifier = Modifier
                                    .padding(
                                        start = 8.dp,
                                        end = 8.dp,
                                        bottom = 6.dp
                                    )
                                    .wrapContentWidth(Alignment.Start),
                                overflow = TextOverflow.Ellipsis,
                                text = note.note_desc,
                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListView(
    modifier: Modifier,
    navController : NavController
){
    val context = LocalContext.current

    val viewModel: HomeScreenViewModel = viewModel(
        factory = HomeScreenViewModelFactory(
            context.applicationContext as Application

        )
    )
    val allNotes = viewModel.notes.observeAsState(listOf())

    val itemCount = allNotes.value!!.count()
    val frameCount = 2

    LazyColumn(
        modifier = Modifier.padding(bottom = 50.dp),        content = {
            items(itemCount) {


                Surface(
                    color = androidx.compose.material3.MaterialTheme.colorScheme.tertiaryContainer,
                    shape = RoundedCornerShape(5.dp),
                    shadowElevation = 6.dp,
                    modifier = Modifier
                        .padding(3.dp)
                        .sizeIn(maxHeight = 150.dp)
                        ,
                    onClick = {

                        val note = allNotes.value!![it]

                        val noteJson = Uri.encode(Gson().toJson(note))

                        navController.navigate("note_details_page/${noteJson}")

                    },
                    content = {

                        Column(
                            modifier = Modifier.padding(5.dp)
                        ) {
                            val note: Notes = allNotes.value[it]

                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(alignment = Alignment.CenterVertically)
                                        .padding(
                                            start = 8.dp,
                                            end = 8.dp,
                                            top = 4.dp
                                        )
                                        .weight(1f)
                                        .wrapContentWidth(Alignment.Start),
                                    text = note.note_title,
                                    overflow = TextOverflow.Ellipsis,
                                    style = androidx.compose.material3.MaterialTheme.typography.labelLarge
                                )
                            }
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp),
                            )


                            Text(
                                modifier = Modifier
                                    .padding(
                                        start = 8.dp,
                                        end = 8.dp,
                                        bottom = 6.dp
                                    )
                                    .wrapContentWidth(Alignment.Start),
                                overflow = TextOverflow.Ellipsis,
                                text = note.note_desc,
                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                            )
                        }
                    }, selected = true

                )
            }
        }
    )
}