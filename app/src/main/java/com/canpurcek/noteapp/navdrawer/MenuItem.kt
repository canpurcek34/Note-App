package com.canpurcek.noteapp.navdrawer

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val id: String,
    val title : String,
    val contentDescription : String,
    val icon : ImageVector
)