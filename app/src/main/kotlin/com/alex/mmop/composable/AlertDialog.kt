package com.alex.mmop.composable

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.alex.mmop.api.LOGS

/**
 * @author alex5402
 * Created 9/22/24 at 12:59 AM
 * AlertDialog
 */
@Composable
fun PermissonDialog(
    onDismiss: () -> Unit ,
    onconfirm : () -> Unit,
    oncancel: () -> Unit ,
    title: String,
    message: String,
    confirmText: String = "Okay"
) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Text(message)
            },
            title = {
                Text(title)
            },
            confirmButton = {
                Text(confirmText,
                    modifier = Modifier.clickable(
                        onClick = onconfirm
                    ))
            },
            dismissButton = {
                Text("Cancel",
                    modifier = Modifier.clickable(
                        onClick = oncancel
                    ),
                    color = Color(0xFFff0000)
                )
            }
        )

}



@Composable
fun UpdateDialog(
    onDismiss: () -> Unit = {},
    onconfirm : () -> Unit = {},
    oncancel : () -> Unit = {},
    title: String,
    message: String,
    confirmText: String = "Download"
) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Text(message)
            },
            title = {
                Text(title)
            },
            confirmButton = {
                Text(confirmText,
                    modifier = Modifier.clickable(
                        onClick = onconfirm
                    )
                )
            },
            dismissButton = {
                Text("Cancel",
                    modifier = Modifier.clickable(
                        onClick = oncancel
                    )
                )
            },
        )

}

