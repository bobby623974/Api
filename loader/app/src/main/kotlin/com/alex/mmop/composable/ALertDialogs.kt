package com.alex.mmop.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alex.mmop.R

/**
 * @author alex5402
 * Created 9/20/24 at 11:52 AM
 * ALertDialogs
 */


@Composable
fun AlertProgressBar(
    modifier: Modifier = Modifier,
    loadingmessage : String
) {

    val compositon by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.normalanimation1))
    val normal = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        color = Color.Black
    )

     Dialog(
         onDismissRequest = { }
     ) {
         Box(
             modifier = modifier
                 .fillMaxWidth()
                 .padding(
                     start = 16.dp,
                     end = 16.dp,
                 )
                 .background(
                     Color(0xFFF5F5F5),
                     shape = RoundedCornerShape(20.dp),
                 )
         ) {

             Column(
                 modifier = Modifier.align(
                     Alignment.Center
                 )
             ) {
                 
                 LottieAnimation(composition = compositon, isPlaying = true,
                     iterations = LottieConstants.IterateForever,
                     modifier = Modifier
                         .fillMaxWidth()
                         .align(Alignment.CenterHorizontally)
                         .padding(top = 20.dp)
                         .height(200.dp)
                     ,
                     renderMode = RenderMode.AUTOMATIC,
                     alignment = Alignment.BottomCenter,
                     contentScale = ContentScale.Fit)

                 Text(
                     text = loadingmessage,
                     color = Color.Black,
                     style = normal,
                     textAlign = TextAlign.Center,
                     modifier = Modifier
                         .align(
                             Alignment.CenterHorizontally
                         )
                         .padding(
                             top = 5.dp
                         )
                 )

             }

         }


     }

}


@Composable
fun AlertProgressBar2(
    modifier: Modifier = Modifier,
    loadingmessage : String
) {

    val compositon by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.copyfileanimation))
    val normal = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        color = Color.Black
    )

    val dialogProperties = DialogProperties(
        dismissOnBackPress = false ,
        dismissOnClickOutside = false
    )
    Dialog(
        onDismissRequest = { },
        properties = dialogProperties
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                )
                .background(
                    Color(0x79F5F5F5),
                    shape = RoundedCornerShape(20.dp),
                )
        ) {

            Column(
                modifier = Modifier.align(
                    Alignment.Center
                )
            ) {

                LottieAnimation(composition = compositon, isPlaying = true,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                        .height(200.dp)
                    ,
                    renderMode = RenderMode.AUTOMATIC,
                    alignment = Alignment.BottomCenter,
                    contentScale = ContentScale.Fit)

                Text(
                    text = loadingmessage,
                    color = Color.Black,
                    style = normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .padding(
                            top = 5.dp
                        )
                )

            }

        }


    }

}

@Preview
@Composable
private fun ProgressBar2() {
    AlertProgressBar2(
        loadingmessage = "Loading..."
    )

}