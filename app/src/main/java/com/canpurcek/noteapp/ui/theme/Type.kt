package com.canpurcek.noteapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.canpurcek.noteapp.R

val Russo = FontFamily(
    Font(R.font.russone_regular, FontWeight.Normal)
)

val Chakra = FontFamily(
    Font(R.font.chakra_bold, FontWeight.Bold),
    Font(R.font.chakra_medium, FontWeight.Medium),
    Font(R.font.chakra_regular, FontWeight.Normal)
)

val Montserrat = FontFamily(

    Font(R.font.montserrat_medium, FontWeight.Medium)
)

val Roboto = FontFamily(

    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_thin, FontWeight.Thin),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_regular, FontWeight.Normal)
)

val OpenSans = FontFamily(
    Font(R.font.sans_light,FontWeight.Light),
    Font(R.font.sans_medium,FontWeight.Medium),
    Font(R.font.sans_regular,FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = Chakra,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    body2 = TextStyle(
        fontFamily = Chakra,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)
