package com.canpurcek.noteapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.navArgument
import com.canpurcek.noteapp.entity.Notes
import com.canpurcek.noteapp.navigation.NavTypo
import com.canpurcek.noteapp.ui.theme.NoteAppTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Surface() {
                NoteAppTheme {
                    PageTrans()

                }
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
            val note = it.arguments?.getParcelable<Notes>("note_id")


            NoteDetailScreen(note!!, navController)

        }


    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Preview(){
    NoteAppTheme {
        PageTrans()
    }
}