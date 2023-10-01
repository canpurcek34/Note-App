package com.canpurcek.noteapp.userinterface

import android.annotation.SuppressLint
import android.text.format.DateFormat.format
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.canpurcek.noteapp.R
import com.canpurcek.noteapp.viewmodel.NoteDetailScreenViewModel
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnrememberedMutableState")
@Composable
fun ColorPicker(
    colors: List<Color>,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier
) {

    val viewModel: NoteDetailScreenViewModel = viewModel()

    var colorPickerOpen by rememberSaveable { mutableStateOf(false) }
    
    var currentlySelected by  mutableStateOf(MaterialTheme.colorScheme.surface)

    IconButton(onClick = {

        colorPickerOpen=true

    }) {
        Icon(
            painter = painterResource(id = R.drawable.palette),
            contentDescription = "note color palette", tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
        )
    }

    if (colorPickerOpen) {
        ColorDialog(
            colorList = colors,
            onDismiss = { colorPickerOpen = false },
            currentlySelected = currentlySelected,
            onColorSelected = {
                currentlySelected = it
                onColorSelected(it)
            }
        )
    }

}


@Composable
fun ColorDialog(
    colorList: List<Color>,
    onDismiss: (() -> Unit),
    currentlySelected: Color,
    onColorSelected: ((Color) -> Unit) // when the save button is clicked
) {

    val gridState = rememberLazyGridState()

    val viewModel: NoteDetailScreenViewModel = viewModel()

    val tFNoteTitle = remember { mutableStateOf("") }
    val tFNote = remember { mutableStateOf("") }

    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.outline,
        onDismissRequest = onDismiss,
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = gridState
            ) {
                items(colorList) { color ->
                    var borderWidth = 0.dp
                    if (currentlySelected == color) {
                        borderWidth = 2.dp
                    }

                    Canvas(modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            borderWidth,
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f),
                            RoundedCornerShape(20.dp)
                        )
                        .background(color)
                        .requiredSize(70.dp)
                        .clickable {

                            onColorSelected(color)
                            onDismiss()
                        }
                    ) {
                    }
                }
            }
        },
        confirmButton = {
        }
    )

}

@Composable
fun AnimateColorAsStateSample(
    needColorChange:Boolean = false,
    startColor: Color = MaterialTheme.colorScheme.primary,
    endColor: Color = MaterialTheme.colorScheme.error
){
    val backgroundColor by animateColorAsState(
        targetValue = if(needColorChange) endColor else startColor,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 50,
            easing = LinearEasing
        )
    )
    
    

}

fun colourSaver() = Saver<MutableState<Color>, String>(
    save = { state -> state.value.toHexString() },
    restore = { value -> mutableStateOf(value.toColor()) }
)

fun Color.toHexString(): String {
    return String.format(
        "#%02x%02x%02x%02x", (this.alpha * 255).toInt(),
        (this.red * 255).toInt(), (this.green * 255).toInt(), (this.blue * 255).toInt()
    )
}

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}
