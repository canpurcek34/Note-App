package com.canpurcek.noteapp.userinterface.main


import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.canpurcek.noteapp.R
import com.canpurcek.noteapp.navdrawer.DrawerBody
import com.canpurcek.noteapp.navdrawer.DrawerHeader
import com.canpurcek.noteapp.navdrawer.MenuItem
import com.canpurcek.noteapp.ui.theme.*
import com.canpurcek.noteapp.viewmodel.HomeScreenViewModel
import com.canpurcek.noteapp.viewmodelfactory.HomeScreenViewModelFactory
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.*


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "InvalidColorHexValue",
    "UnrememberedMutableState", "RememberReturnType", "CoroutineCreationDuringComposition"
)
@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current

    val viewModel: HomeScreenViewModel = viewModel(
        factory = HomeScreenViewModelFactory(
            context.applicationContext as Application))

    val isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)



    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.loadNotes()
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.loadNotes()
        },
        indicator = { state, trigger ->
            CustomViewPullRefreshView(state, trigger)
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
            topBar = {
                com.canpurcek.noteapp.userinterface.AppBar(
                    onNavigationIconClick = {

                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }, navController
                )
            },
            drawerContent = {
                            DrawerHeader()
                            DrawerBody(
                                items = listOf(
                                    MenuItem(
                                        id = "home",
                                        title = "Anasayfa",
                                        contentDescription = "Home screen navigation",
                                        icon = Icons.Default.Home
                                    )
                                    ,
                                    MenuItem(
                                        id = "settings",
                                        title = "Ayarlar",
                                        contentDescription = "Settings screen navigation",
                                        icon = Icons.Default.Settings
                                    ),

                                ),
                                modifier = Modifier,
                                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
                                onItemClick ={
                                    when (it.id){
                                        "home" -> navController.navigate("main_page")
                                        "settings" -> navController.navigate("settings_page")
                                    }
                                }
                            )
            },
            content = {

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
                                painter = painterResource(id = R.drawable.check),
                                contentDescription = "check list",
                                tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.brush),
                                contentDescription = "drawing note",
                                tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.mic),
                                contentDescription = "voice note",
                                tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.imagesmode),
                                contentDescription = "note with images",
                                tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }, cutoutShape = RoundedCornerShape(CornerSize(12.dp))

                )
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.size(50.dp, 50.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = {

                        navController.navigate("note_add_page")
                    },
                    backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondaryContainer
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

