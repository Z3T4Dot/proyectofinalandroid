package com.example.libros.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    h6 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    body1 = TextStyle(
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Light
    ),
    button = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    )
)
