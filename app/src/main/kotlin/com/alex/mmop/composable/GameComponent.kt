package com.alex.mmop.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alex.mmop.R
import com.alex.mmop.ui.theme.Selectgametheme

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun GameComponent(
    imageid: Int,
    buttonText: String = "INSTALL",
    onclick: () -> Unit,
    isenabled: Boolean = true,
    appclosed: () -> Unit = {},
) {
    Card(
        modifier = Modifier.padding(
            top = 20.dp,
            start = 10.dp,
            end = 10.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 10.dp, end = 20.dp)
        ) {
            // Game logo at the top with bigger gap and rectangle shape
            Image(
                painter = painterResource(id = imageid),
                contentDescription = "Game Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth() // Make image width fill
                    .height(150.dp) // Set fixed height for image
                    .padding(20.dp) // Add padding for bigger gap
                    .clickable(onClick = appclosed)
            )

            // Centered Box for buttons with bigger gap
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp) // Increased gap
            ) {
                // Open Game Button (Smaller and rectangle shape)
                Box(
                    modifier = Modifier
                        .size(100.dp, 50.dp) // Smaller and rectangular shape
                        .clip(RoundedCornerShape(8.dp)) // Rounded rectangle shape
                        .background(Color(0xFF0E97FD))
                        .clickable(onClick = onclick)
                        .padding(10.dp)
                        .align(Alignment.CenterStart), // Align to the left
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buttonText,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            fontSize = 16.sp, // Font size
                            color = Color.White,
                            fontFamily = FontFamily.Monospace
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                // Stop Game Button (Smaller and rectangle shape)
                Box(
                    modifier = Modifier
                        .size(100.dp, 50.dp) // Smaller and rectangular shape
                        .clip(RoundedCornerShape(8.dp)) // Rounded rectangle shape
                        .background(Color.Red)
                        .clickable(onClick = appclosed)
                        .padding(10.dp)
                        .align(Alignment.CenterEnd), // Align to the right
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "STOP",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp, // Font size
                            color = Color.White,
                            fontFamily = FontFamily.Monospace
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
