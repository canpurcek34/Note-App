package com.canpurcek.noteapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.navArgument
import com.canpurcek.noteapp.entity.Notes
import com.canpurcek.noteapp.navigation.NavTypo
import com.canpurcek.noteapp.ui.theme.NoteAppTheme
import com.canpurcek.noteapp.userinterface.mainscreens.HomeScreen
import com.canpurcek.noteapp.userinterface.UserProfile
import com.canpurcek.noteapp.userinterface.mainscreens.AddNoteScreen
import com.canpurcek.noteapp.userinterface.settingScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


var darkMode by mutableStateOf(true)

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    @OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContent {

            Surface() {
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
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController, startDestination = "main_page" ){


        composable("main_page",
            enterTransition ={
                slideIn(
                    // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                    animationSpec = tween(durationMillis = 800),
                    initialOffset = {
                        IntOffset.Zero
                    }
                )
            }, exitTransition  ={
                slideOut(
                    // Overwrites the default animation with tween
                    animationSpec = tween(durationMillis = 600),
                    targetOffset = {
                        IntOffset.Zero
                    }
                )
            }
        ){
            HomeScreen(navController)
        }

        composable("note_add_page",
            enterTransition ={
                fadeIn(
                    // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                    initialAlpha = 0.4f,animationSpec = tween(durationMillis = 800)
                )
            }, exitTransition  ={
                fadeOut(
                    // Overwrites the default animation with tween
                    animationSpec = tween(durationMillis = 600)
                )
            }
        ){
            AddNoteScreen(navController)
        }


        composable(
            "note_details_page/{note_id}",
            arguments = listOf(
                navArgument("note_id"){
                    type = NavTypo()
                }
            ), enterTransition ={
                slideIn(
                    // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                    animationSpec = tween(durationMillis = 400),
                    initialOffset = {
                        IntOffset.Zero
                    }
                )
            }, exitTransition  ={
                slideOut(
                    // Overwrites the default animation with tween
                    animationSpec = tween(durationMillis = 400),
                    targetOffset = {
                        IntOffset.Zero
                    }
                )
            }

        ){
            val note = it.arguments?.getParcelable<Notes>("note_id")


            NoteDetailScreen(note!!, navController)

        }

        composable("settings_page",
            enterTransition ={
                fadeIn(
                    // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                    initialAlpha = 0.4f,animationSpec = tween(durationMillis = 800)
                )
            }, exitTransition  ={
                fadeOut(
                    // Overwrites the default animation with tween
                    animationSpec = tween(durationMillis = 600)
                )
            }
        ){
           settingScreen(navController)
        }

        composable("profile_page",
            enterTransition ={
                fadeIn(
                    // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                    initialAlpha = 0.4f,animationSpec = tween(durationMillis = 800)
                )
            }, exitTransition  ={
                fadeOut(
                    // Overwrites the default animation with tween
                    animationSpec = tween(durationMillis = 600)
                )
            }
        ){
            UserProfile(navController)
        }


    }
}

@Composable
fun ThemeSwitch(
modifier: Modifier
) {
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
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}


@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Preview(){
    NoteAppTheme(
        useDarkTheme = isSystemInDarkTheme(),
        content = {
            PageTrans()
        }
    )
}