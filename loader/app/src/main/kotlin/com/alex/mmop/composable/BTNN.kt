package com.alex.mmop.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author alex5402
 * Created 9/28/24 at 8:55 PM
 * BTNN
 */
@Composable
fun TransparentButton(
    modifier: Modifier = Modifier,
    buttonName: String,
    onClick: () -> Unit,
    textColour: Color = themedtextcolour(),
    cornerRadius: Int = 20
) {
    val caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 12.sp,
    )
    Box(
        modifier = modifier
            .defaultMinSize(
                minHeight = 50.dp
            )
            .border(
                color = Color.LightGray,
                width = 1.dp,
                shape = RoundedCornerShape(cornerRadius)
            )
            .clickable(onClick = onClick, role = Role.Button)
    ) {
        Text(
            text = buttonName,
            style = caption,
            fontWeight = FontWeight.Bold,
            color = textColour,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    top = 5.dp,
                    bottom = 5.dp,
                )
        )
    }
}

@Preview
@Composable
private fun TransparentButtonPreview() {

    TransparentButton(
        buttonName = "Button",
        onClick = {

        }
    )
}

