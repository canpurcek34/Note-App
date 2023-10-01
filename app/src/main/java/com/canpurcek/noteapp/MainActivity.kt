package com.canpurcek.noteapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.canpurcek.noteapp.ui.theme.NoteAppTheme
import com.canpurcek.noteapp.userinterface.main.HomeScreen
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.canpurcek.noteapp.retrofit.JSON.Notebook
import com.canpurcek.noteapp.userinterface.main.AddNoteScreen
import com.canpurcek.noteapp.userinterface.main.NoteDetailScreen
import com.google.gson.Gson


var darkMode by mutableStateOf(false)

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    @OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                NoteAppTheme(
                    useDarkTheme = darkMode,
                    content = {

                    PageTrans()

                    }
                )
            }
        }
    }
}
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PageTrans() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_page") {
        composable("main_page") {
            HomeScreen(navController)
        }
        composable("note_add_page") {
            AddNoteScreen(navController)
        }
        composable("note_details_page/{id}", arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        )) {
            val json = it.arguments?.getString("id")
            val obj = Gson().fromJson(json, Notebook::class.java)
            NoteDetailScreen(obj,navController)
        }
    }

    @Composable
    fun ThemeSwitch() {
        Box(modifier = Modifier.fillMaxSize(1f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                androidx.compose.material3.Switch(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 35.dp),
                    checked = darkMode,
                    onCheckedChange = { darkMode = !darkMode })

                Spacer(modifier = Modifier.width(36.dp))

                Text(
                    text = if (darkMode) "Karanlık mod" else "Aydınlık mod",
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}


