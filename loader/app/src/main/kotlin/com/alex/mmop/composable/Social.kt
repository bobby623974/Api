package com.alex.mmop.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

/**
 * @author alex5402
 * Created 9/28/24 at 3:40 PM
 * Social
 */
@Composable
fun SocialApps(
    onclick : () -> Unit,
    appname : String = "Default app",
    imageid : Int
) {
    val boldfontstyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        fontSize = 20.sp,
        fontFamily = FontFamily.Monospace,
    )
    Card(
        modifier = Modifier.padding(10.dp)
            .defaultMinSize(
                minHeight = 100.dp,
                minWidth = 100.dp,
            )
    ) {
        Column (
            modifier = Modifier
                .clickable(
                    onClick = onclick,
                    onClickLabel = appname
                )
        ) {
            Image(
                painter = painterResource(id = imageid),
                contentDescription = appname,
                modifier = Modifier.padding(10.dp)
                    .defaultMinSize(
                        minHeight = 80.dp,
                        minWidth = 80.dp,
                    ).size(
                        height = 80.dp,
                        width = 80.dp,
                    ).clip(CircleShape).align(
                        Alignment.CenterHorizontally
                    )
            )
            Text(appname ,
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ).basicMarquee()
                ,
                style = boldfontstyle,
                textAlign = TextAlign.Center

            )
        }
    }
}

@Preview
@Composable
private fun SocialPrew() {
    SocialApps(
        onclick = {} ,
        imageid = R.drawable.facebook,
    )
}