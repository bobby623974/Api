package com.alex.mmop.composable


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun themedtextcolour(): Color
{
    val themedColor = if (isSystemInDarkTheme()){
        Color(0xFF20FFF8)
    } else {
        Color(0xFF181B65)
    }
    return themedColor
}


@Composable
fun themetextcolour2(): Color
{
    val themedColor = if (isSystemInDarkTheme()){
        Color.White
    } else {
        Color.Black
    }
    return themedColor
}

@Composable
fun registercolour(): Color
{
    val themedColor = if (isSystemInDarkTheme()){
        Color(0xFF52FF00)
    } else {
        Color(0xFF00B7FF)
    }
    return themedColor
}