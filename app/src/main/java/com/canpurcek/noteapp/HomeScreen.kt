package com.canpurcek.noteapp


import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.FabPosition
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.canpurcek.noteapp.entity.Notes
import com.canpurcek.noteapp.room.dataBase
import com.canpurcek.noteapp.ui.theme.*
import com.canpurcek.noteapp.viewmodel.HomeScreenViewModel
import com.canpurcek.noteapp.viewmodelfactory.HomeScreenViewModelFactory
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.*
import com.google.gson.Gson


@RequiresApi(Build.VERSION_CODES.O)
@OptIn( ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "InvalidColorHexValue")
@Composable
fun HomeScreen(navController: NavController){

    val context = LocalContext.current

    val viewModel: HomeScreenViewModel = viewModel(
        factory = HomeScreenViewModelFactory(context.applicationContext as Application

        )
    )
    val allNotes = viewModel.notes.observeAsState(listOf())

    val itemCount = allNotes.value!!.count()
    val frameCount = 2

    LaunchedEffect(key1 = true ){
        viewModel.loadNotes()


    }

    val isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)


    //listview changer
    var checked by remember { mutableStateOf(false) }




    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
                   viewModel.loadNotes()
        },
    indicator = {state, trigger ->
    CustomViewPullRefreshView(state, trigger)}
    ) {
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White,
            content ={

                Column(
                    modifier = Modifier.background(color = Color.White),
                    verticalArrangement = Arrangement.SpaceBetween) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(top = 10.dp)
                    ) {

                        Card(
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .height(50.dp),
                            shape = CircleShape,
                            backgroundColor = SearchColor
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                content ={

                                    IconButton(modifier = Modifier.padding(start = 2.dp),
                                        onClick = { /*TODO*/ }) {
                                        Icon(painter = painterResource(id = R.drawable.menu_drawer),
                                            contentDescription = "menu drawer")
                                    }

                                    Text(
                                        modifier = Modifier,
                                        text = "Notlarınızda arayın" ,
                                        color = Color.DarkGray,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = Roboto,
                                        fontStyle = FontStyle.Normal,
                                        fontWeight = FontWeight.Light
                                    )
                                    )

                                    IconToggleButton(modifier = Modifier.padding(start = 50.dp),
                                    checked = checked,
                                    onCheckedChange = {
                                        checked = it
                                    }) {


                                        if(checked){
                                            Icon(painter = painterResource(id = R.drawable.gridview),
                                                contentDescription = "screen")
                                        }else {

                                            Icon(
                                                painter = painterResource(id = R.drawable.splitscreen),
                                                contentDescription = "screen"
                                            )
                                        }
                                    }

                                    IconButton(modifier = Modifier.padding(end = 2.dp),
                                        onClick = { /*TODO*/ }) {
                                        Icon(painter = painterResource(id = R.drawable.person),
                                            contentDescription = "person")
                                    }
                                }
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                    )

                    Row(
                        modifier = Modifier
                            .padding(all = 5.dp),

                        content = {

                            //change list view ui
                            if (!checked) {

                            LazyVerticalStaggeredGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 50.dp),
                                userScrollEnabled = true,
                                columns = StaggeredGridCells.Fixed(frameCount),
                                content = {
                                    items(itemCount) {

                                        val choosedColor: Color = Brown

                                        Card(
                                            backgroundColor = choosedColor,
                                            modifier = Modifier
                                                .padding(3.dp)
                                                .sizeIn(maxHeight = 250.dp)
                                                .combinedClickable(onClick = {

                                                    val note = allNotes.value!![it]

                                                    val noteJson = Uri.encode(Gson().toJson(note))

                                                    navController.navigate("note_details_page/${noteJson}")

                                                }
                                                ),
                                            shape = RoundedCornerShape(12.dp),
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
                                                            text = note.note_title.toString(),
                                                            overflow = TextOverflow.Ellipsis,
                                                            style = TextStyle(
                                                                color = cont_normal,
                                                                fontSize = 16.sp,
                                                                fontFamily = OpenSans,
                                                                fontStyle = FontStyle.Normal,
                                                                fontWeight = FontWeight.W500
                                                            )
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
                                                        text = note.note_desc.toString(),
                                                        style = TextStyle(
                                                            color = cont_minor,
                                                            fontSize = 14.sp,
                                                            fontFamily = OpenSans,
                                                            fontStyle = FontStyle.Normal,
                                                            fontWeight = FontWeight.W400
                                                        )
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            )
                        }else{

                            LazyColumn(
                                modifier = Modifier.padding(bottom = 50.dp),
                                content ={
                                    items(itemCount) {

                                        val choosedColor: Color = Brown

                                       androidx.compose.material3.Surface(
                                            color = choosedColor,
                                            modifier = Modifier
                                                .padding(3.dp)
                                                .fillMaxWidth()
                                                .sizeIn(maxHeight = 150.dp)
                                                ,
                                           onClick = {

                                               val note = allNotes.value!![it]

                                               val noteJson = Uri.encode(Gson().toJson(note))

                                               navController.navigate("note_details_page/${noteJson}")

                                           },
                                            shape = RoundedCornerShape(12.dp),
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
                                                            text = note.note_title.toString(),
                                                            overflow = TextOverflow.Ellipsis,
                                                            style = TextStyle(
                                                                color = cont_normal,
                                                                fontSize = 16.sp,
                                                                fontFamily = OpenSans,
                                                                fontStyle = FontStyle.Normal,
                                                                fontWeight = FontWeight.W500
                                                            )
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
                                                        text = note.note_desc.toString(),
                                                        style = TextStyle(
                                                            color = cont_minor,
                                                            fontSize = 14.sp,
                                                            fontFamily = OpenSans,
                                                            fontStyle = FontStyle.Normal,
                                                            fontWeight = FontWeight.W400
                                                        )
                                                    )
                                                }
                                            }, selected = true

                                        )
                                    }
                                }
                            )


                        }
                        }
                    )
                }

            },
        bottomBar = {

                androidx.compose.material.BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    backgroundColor = BottomAppBarDefaults.containerColor,
                    content = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(painter = painterResource(id = R.drawable.check), contentDescription = "check list")
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.brush),
                                contentDescription = "drawing note",
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.mic),
                                contentDescription = "voice note",
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.imagesmode),
                                contentDescription = "note with images",
                            )
                        }
                    }, cutoutShape = RoundedCornerShape(CornerSize(12.dp))

                )
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.size(50.dp,50.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = {

                        navController.navigate("note_add_page")
                    },
                    containerColor = BottomAppBarDefaults.bottomAppBarFabColor
                ) {
                    Icon(Icons.Filled.Add, "fab button")
                }
        }
        )
    }
}

@Composable
fun CustomViewPullRefreshView(
    swipeRefreshState: SwipeRefreshState,
    refreshTriggerDistance: Dp,
    color: Color = Color.Blue
) {
    Box(
        Modifier
            .drawWithCache {
                onDrawBehind {
                    val distance = refreshTriggerDistance.toPx()
                    val progress = (swipeRefreshState.indicatorOffset / distance).coerceIn(0f, 1f)
                    val brush = Brush.verticalGradient(
                        0f to color.copy(alpha = 0.45f),
                        1f to color.copy(alpha = 0f)
                    )
                    drawRect(
                        brush = brush,
                        alpha = FastOutSlowInEasing.transform(progress)
                    )
                }
            }
            .fillMaxWidth()
            .height(80.dp)
    ) {
        if (swipeRefreshState.isRefreshing) {
            LinearProgressIndicator(
                Modifier.fillMaxWidth(),
                color = Color.Green
            )
        } else {
            val trigger = with(LocalDensity.current) { refreshTriggerDistance.toPx() }
            val progress = (swipeRefreshState.indicatorOffset / trigger).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
        }
    }


}


