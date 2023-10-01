package com.canpurcek.noteapp.userinterface

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.canpurcek.noteapp.ui.theme.Roboto
import com.canpurcek.noteapp.R
import com.canpurcek.noteapp.userinterface.notegrid.GridView
import com.canpurcek.noteapp.userinterface.notegrid.ListView

@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit,
    navController: NavController
){
    //listview changer
    var checked by remember { mutableStateOf(false) }

    var screen by rememberSaveable { mutableStateOf("list_view")}

    TopAppBar(
        modifier = Modifier,
        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 5.dp, bottom = 5.dp)
        ) {

            Card(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .height(50.dp),
                shape = CircleShape,
                backgroundColor = MaterialTheme.colors.onPrimary
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    content = {


                        IconButton(
                            modifier = Modifier
                            .padding(start = 2.dp),
                            onClick = onNavigationIconClick
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.menu_drawer),
                                contentDescription = "menu drawer",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }

                        Text(
                            modifier = Modifier,
                            text = "Notlarınızda arayın",
                            color = MaterialTheme.colors.onSurface,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = Roboto,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Light
                            )
                        )

                        IconToggleButton(
                            modifier = Modifier.padding(start = 50.dp),
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                            }) {


                            if (checked) {

                                screen = "grid_view"

                                Icon(
                                    painter = painterResource(id = R.drawable.gridview),
                                    contentDescription = "grid_view",
                                    tint = MaterialTheme.colors.onSurface
                                )
                            } else {

                                screen = "list_view"

                                Icon(
                                    painter = painterResource(id = R.drawable.splitscreen),
                                    contentDescription = "list_view",
                                    tint = MaterialTheme.colors.onSurface
                                )
                            }
                        }

                        IconButton(modifier = Modifier.padding(end = 2.dp),
                            onClick = {

                                navController.navigate("profile_page")

                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.person),
                                contentDescription = "person",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                )
            }
        }
    }
    Navigation(screen, Modifier) { currentScreen ->
        if (currentScreen == "list_view") {
            GridView(navController = navController)
        } else {
            ListView(modifier = Modifier, navController =navController )
        }
    }

}
@Composable
private fun <T : Any> Navigation(
    currentScreen: T,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    val saveableStateHolder = rememberSaveableStateHolder()
    Box(modifier.padding(start = 10.dp, top = 60.dp, end = 10.dp).fillMaxSize()) {
        saveableStateHolder.SaveableStateProvider(currentScreen) {
            content(currentScreen)
        }
    }
}