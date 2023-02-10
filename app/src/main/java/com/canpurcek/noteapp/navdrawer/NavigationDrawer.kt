package com.canpurcek.noteapp.navdrawer

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.Switch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canpurcek.noteapp.ThemeSwitch

@Composable
fun DrawerHeader(
    modifier: Modifier,
    backgroundColor: androidx.compose.ui.graphics.Color
){
    Box(modifier = Modifier
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .fillMaxWidth()
        .padding(vertical = 16.dp)
        .padding(start = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = "Merhaba!", fontSize = 30.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier,
    backgroundColor: androidx.compose.ui.graphics.Color,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp,color = MaterialTheme.colorScheme.onSecondaryContainer),
    onItemClick: (MenuItem) -> Unit
){
    var darkMode by mutableStateOf(true)

    Column(
         modifier = Modifier
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .fillMaxWidth()
    ) {

        LazyColumn(modifier) {
            items(items) { item ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = item.icon,
                        contentDescription = item.contentDescription,
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = item.title,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSecondaryContainer
                    )

                }


            }


        }

        ThemeSwitch(modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer))
    }

}

